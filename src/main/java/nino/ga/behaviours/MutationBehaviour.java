package nino.ga.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import nino.ga.utils.ArrayConverter;
import nino.ga.AgentsList;

import java.util.List;
import java.util.Random;

public class MutationBehaviour extends OneShotBehaviour {
    private double mutationRate = 0.78; // Tasa de mutación

    public MutationBehaviour(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        // Combinar filtros para recibir ambos tipos de mensajes
        MessageTemplate template = MessageTemplate.or(
                MessageTemplate.MatchConversationId("CROSSOVER-DATA"),
                MessageTemplate.MatchConversationId("NEXT-GENERATION")
        );

        ACLMessage msg = myAgent.blockingReceive(template, 5000); // Espera hasta 5 segundos

        if (msg != null) {
            String serializedData = msg.getContent();
            System.out.println("MutationBehaviour: Datos recibidos con ID de conversación: " + msg.getConversationId());

            // Deserializar datos
            List<double[]> deserialized = ArrayConverter.toArray(serializedData);
            double[] b0 = deserialized.get(0);
            double[] b1 = deserialized.get(1);

            // Validación de la población recibida
            if (b0.length != b1.length) {
                System.err.println("Error: Tamaños inconsistentes en b0 y b1.");
                return;
            }

            System.out.println("Población antes de la mutación:");
            for (int i = 0; i < b0.length; i++) {
                System.out.println("Individuo " + i + ": b0=" + b0[i] + ", b1=" + b1[i]);
            }

            // Construir población para mutación
            double[][] population = new double[b0.length][2];
            for (int i = 0; i < b0.length; i++) {
                population[i][0] = b0[i];
                population[i][1] = b1[i];
            }

            // Aplicar mutaciones
            double[][] mutatedPopulation = applyMutation(population);

            // Serializar población mutada
            double[] mutatedB0 = new double[mutatedPopulation.length];
            double[] mutatedB1 = new double[mutatedPopulation.length];
            for (int i = 0; i < mutatedPopulation.length; i++) {
                mutatedB0[i] = mutatedPopulation[i][0];
                mutatedB1[i] = mutatedPopulation[i][1];
            }
            String mutatedPopulationSerialized = ArrayConverter.toString(mutatedB0, mutatedB1);

            System.out.println("Población después de la mutación:");
            for (int i = 0; i < mutatedB0.length; i++) {
                System.out.println("Individuo " + i + ": b0=" + mutatedB0[i] + ", b1=" + mutatedB1[i]);
            }

            // Enviar datos mutados al EvolutionAgent
            ACLMessage res = new ACLMessage(ACLMessage.REQUEST);
            res.addReceiver(myAgent.getAID(AgentsList.AGENT_EVOLUTION));
            res.setConversationId("NEXT-GENERATION");
            res.setContent(mutatedPopulationSerialized);
            myAgent.send(res);

            System.out.println("MutationBehaviour: Población mutada enviada al EvolutionAgent.");
        } else {
            System.out.println("MutationBehaviour: No se recibieron datos dentro del tiempo esperado.");
        }

        // Reprogramar este comportamiento
        myAgent.addBehaviour(new MutationBehaviour(myAgent));
    }

    private double[][] applyMutation(double[][] population) {
        Random random = new Random();

        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (random.nextDouble() < mutationRate) {
                    // Aplicar mutación aleatoria
                    double mutation = random.nextGaussian() * 5;
                    population[i][j] += mutation;

                    // Garantizar que los valores permanezcan dentro de un rango razonable
                    population[i][j] = Math.max(-500, Math.min(500, population[i][j]));
                }
            }
        }
        return population;
    }
}
