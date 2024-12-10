package nino.ga.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import nino.ga.AgentsList;
import nino.ga.utils.ArrayConverter;

import java.util.List;

public class FitnessBehaviour extends OneShotBehaviour {
    public FitnessBehaviour(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        // Recibir datos de población
        MessageTemplate template = MessageTemplate.MatchConversationId("POPULATION-DATA");
        ACLMessage msg = myAgent.blockingReceive(template, 5000); // Tiempo de espera de 5 segundos

        if (msg != null) {
            String serializedPopulation = msg.getContent();
            System.out.println("FitnessBehaviour: Población recibida.");

            // Deserializar población
            List<double[]> deserialized = ArrayConverter.toArray(serializedPopulation);
            double[] b0 = deserialized.get(0);
            double[] b1 = deserialized.get(1);

            // Validación de la población
            if (b0.length != b1.length) {
                System.err.println("Error: La población deserializada tiene tamaños inconsistentes para b0 y b1.");
                return;
            }

            System.out.println("Población inicial:");
            for (int i = 0; i < b0.length; i++) {
                System.out.println("Individuo " + i + ": b0=" + b0[i] + ", b1=" + b1[i]);
            }

            // Calcular fitness
            double[] fitness = calculateFitness(b0, b1);

            System.out.println("Fitness calculado:");
            for (int i = 0; i < fitness.length; i++) {
                System.out.println("Individuo " + i + ": fitness=" + fitness[i]);
            }

            // Normalizar fitness
            double totalFitness = 0.0;
            for (double fit : fitness) {
                totalFitness += fit;
            }
            for (int i = 0; i < fitness.length; i++) {
                fitness[i] /= totalFitness;
            }

            // Serializar fitness y población
            String serializedFitness = ArrayConverter.toString(fitness, new double[0]);
            String serializedData = serializedPopulation + "|" + serializedFitness; // Concatenar población y fitness

            // Enviar datos combinados al SelectionAgent
            ACLMessage res = new ACLMessage(ACLMessage.REQUEST);
            res.addReceiver(myAgent.getAID(AgentsList.AGENT_SELECTION));
            res.setConversationId("FITNESS-POPULATION-DATA");
            res.setContent(serializedData);
            myAgent.send(res);

            System.out.println("FitnessBehaviour: Fitness y población enviados al SelectionAgent.");
        } else {
            System.out.println("FitnessBehaviour: Tiempo de espera agotado. No se recibió población.");
        }
    }

    private double[] calculateFitness(double[] b0, double[] b1) {
        int size = b0.length;
        double[] fitness = new double[size];
        double yTarget = 30;

        for (int i = 0; i < size; i++) {
            double y = b0[i] + b1[i] * 2;

            // Modificación: Penalización más agresiva
            fitness[i] = Math.pow(1 / (1 + Math.abs(yTarget - y)), 2);
        }

        return fitness;
    }
}
