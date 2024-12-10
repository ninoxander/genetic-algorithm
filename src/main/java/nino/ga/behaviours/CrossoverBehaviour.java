package nino.ga.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import nino.ga.AgentsList;
import nino.ga.utils.ArrayConverter;

import java.util.List;
import java.util.Random;

public class CrossoverBehaviour extends OneShotBehaviour {
    public CrossoverBehaviour(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        // Recibir datos de selección
        MessageTemplate template = MessageTemplate.MatchConversationId("SELECTION-DATA");
        ACLMessage msg = myAgent.blockingReceive(template, 5000); // Tiempo de espera de 5 segundos

        if (msg != null) {
            String serializedSelection = msg.getContent();
            System.out.println("CrossoverBehaviour: Datos de selección recibidos.");

            // Deserializar datos
            List<double[]> deserialized = ArrayConverter.toArray(serializedSelection);
            double[] b0 = deserialized.get(0);
            double[] b1 = deserialized.get(1);

            // Validación de datos
            if (b0.length != b1.length) {
                System.err.println("Error: Los tamaños de b0 y b1 no coinciden.");
                return;
            }

            // Realizar crossover
            double[][] crossedPopulation = applyCrossover(b0, b1);

            // Serializar población cruzada
            double[] newB0 = new double[crossedPopulation.length];
            double[] newB1 = new double[crossedPopulation.length];
            for (int i = 0; i < crossedPopulation.length; i++) {
                newB0[i] = crossedPopulation[i][0];
                newB1[i] = crossedPopulation[i][1];
            }
            String serializedCrossover = ArrayConverter.toString(newB0, newB1);

            // Enviar datos al MutationAgent
            ACLMessage res = new ACLMessage(ACLMessage.REQUEST);
            res.addReceiver(myAgent.getAID(AgentsList.AGENT_MUTATION));
            res.setConversationId("CROSSOVER-DATA");
            res.setContent(serializedCrossover);
            myAgent.send(res);

            System.out.println("CrossoverBehaviour: Crossover realizado y enviado al MutationAgent.");
        } else {
            System.out.println("CrossoverBehaviour: Tiempo de espera agotado. No se recibieron datos de selección.");
        }
    }

    private double[][] applyCrossover(double[] b0, double[] b1) {
        Random random = new Random();
        double crossoverRate = 0.8; // Tasa de crossover (80%)
        double[][] newPopulation = new double[b0.length][2];

        for (int i = 0; i < b0.length; i += 2) {
            if (i + 1 < b0.length && random.nextDouble() < crossoverRate) {
                // Selecciona dos puntos de cruce aleatorios dentro de los límites
                int point1 = random.nextInt(2); // Solo hay dos genes (b0 y b1), rango [0, 2)
                int point2 = random.nextInt(2);

                // Asegura que point1 < point2
                if (point1 > point2) {
                    int temp = point1;
                    point1 = point2;
                    point2 = temp;
                }

                // Realiza el cruce entre los dos padres
                for (int j = 0; j < 2; j++) { // Solo hay dos genes por individuo
                    if (j < point1 || j > point2) {
                        newPopulation[i][j] = b0[j];
                        newPopulation[i + 1][j] = b1[j];
                    } else {
                        newPopulation[i][j] = b1[j];
                        newPopulation[i + 1][j] = b0[j];
                    }
                }
            } else {
                // Sin crossover: copiar los padres
                newPopulation[i][0] = b0[i];
                newPopulation[i][1] = b1[i];
                if (i + 1 < b0.length) {
                    newPopulation[i + 1][0] = b0[i + 1];
                    newPopulation[i + 1][1] = b1[i + 1];
                }
            }
        }

        return newPopulation;
    }
}
