package nino.ga.behaviours;

import jade.core.behaviours.Behaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import nino.ga.AgentsList;

public class EvolutionBehaviour extends Behaviour {
    private int maxGenerations;
    private int currentGeneration = 0;
    private boolean isInitialized = false;

    public EvolutionBehaviour(Agent agent, int maxGenerations) {
        super(agent);
        this.maxGenerations = maxGenerations;
    }

    @Override
    public void action() {
        if (!isInitialized) {
            // Enviar mensaje inicial al PopulationAgent
            ACLMessage initMsg = new ACLMessage(ACLMessage.REQUEST);
            initMsg.addReceiver(myAgent.getAID(AgentsList.AGENT_POPULATION));
            initMsg.setConversationId("POPULATION-INIT");
            initMsg.setContent("START");
            myAgent.send(initMsg);

            isInitialized = true;
        } else {
            // Esperar respuesta del MutationAgent
            MessageTemplate mutationTemplate = MessageTemplate.MatchConversationId("NEXT-GENERATION");
            ACLMessage mutationResponse = myAgent.blockingReceive(mutationTemplate, 5000); // Timeout de 5 segundos

            if (mutationResponse != null) {
                String mutatedPopulation = mutationResponse.getContent();
                currentGeneration++;
                System.out.println("----- Generación " + currentGeneration + " de " + maxGenerations + " procesada -----");

                // Enviar la población mutada al MutationAgent para la siguiente generación
                if (currentGeneration < maxGenerations) {
                    ACLMessage nextGenMsg = new ACLMessage(ACLMessage.REQUEST);
                    nextGenMsg.addReceiver(myAgent.getAID(AgentsList.AGENT_MUTATION));
                    nextGenMsg.setConversationId("NEXT-GENERATION");
                    nextGenMsg.setContent(mutatedPopulation);
                    myAgent.send(nextGenMsg);
                    // System.out.println("EvolutionBehaviour: Población enviada al MutationAgent para la siguiente generación.");
                    System.out.println(mutatedPopulation);
                }
            } else {
                System.out.println("EvolutionBehaviour: No se recibió respuesta del MutationAgent. Reintentando...");
            }
        }
    }

    @Override
    public boolean done() {
        if (currentGeneration >= maxGenerations) {
            System.out.println("Evolución completada tras " + maxGenerations + " generaciones.");

            // Cálculo de R² y valores de beta finales
            double finalBeta0 = 0.0;
            double finalBeta1 = 0.0;
            double rSquared = 0.0;

            try {
                // Obtener la última población mutada
                String[] finalPopulation = getLastPopulation().split(";");
                double[] beta0Array = parseArray(finalPopulation[0]);
                double[] beta1Array = parseArray(finalPopulation[1]);

                // Calcular valores finales
                finalBeta0 = calculateAverage(beta0Array);
                finalBeta1 = calculateAverage(beta1Array);

                // Simula el cálculo de R² basado en un conjunto de datos (puedes adaptar esto a tu dataset real)
                double[] yObserved = {10, 20, 30}; // Valores observados
                double[] xValues = {1, 2, 3};     // Valores independientes
                rSquared = calculateRSquared(yObserved, xValues, finalBeta0, finalBeta1);
            } catch (Exception e) {
                System.err.println("Error al calcular R² o betas finales: " + e.getMessage());
            }

            // Imprimir resultados
            System.out.println("R^2 Final: " + rSquared);
            System.out.println("Valores finales de beta:");
            System.out.println("beta_0: " + finalBeta0);
            System.out.println("beta_1: " + finalBeta1);

            return true;
        }
        return false;
    }

    // Métodos auxiliares para el cálculo
    private double calculateAverage(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private double calculateRSquared(double[] yObserved, double[] xValues, double beta0, double beta1) {
        double ssTotal = 0.0;
        double ssResidual = 0.0;
        double yMean = calculateAverage(yObserved);

        for (int i = 0; i < yObserved.length; i++) {
            double yPredicted = beta0 + beta1 * xValues[i];
            ssResidual += Math.pow(yObserved[i] - yPredicted, 2);
            ssTotal += Math.pow(yObserved[i] - yMean, 2);
        }
        return 1 - (ssResidual / ssTotal);
    }

    private double[] parseArray(String serializedArray) {
        String[] items = serializedArray.split(",");
        double[] array = new double[items.length];
        for (int i = 0; i < items.length; i++) {
            array[i] = Double.parseDouble(items[i]);
        }
        return array;
    }

    private String getLastPopulation() {
        // Simula la última población mutada; reemplaza esto con tu implementación para obtener la población real
        return "19.086279095089914,18.5;5.456492165644436,5.7";
    }

}
