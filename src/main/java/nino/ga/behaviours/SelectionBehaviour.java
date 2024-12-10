package nino.ga.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import nino.ga.AgentsList;
import nino.ga.utils.ArrayConverter;
import nino.ga.utils.RouletteSelection;

import java.util.List;

public class SelectionBehaviour extends OneShotBehaviour {
    public SelectionBehaviour(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        // Recibir datos combinados de población y fitness
        MessageTemplate template = MessageTemplate.MatchConversationId("FITNESS-POPULATION-DATA");
        ACLMessage msg = myAgent.blockingReceive(template);

        if (msg != null) {
            String serializedData = msg.getContent();
            // System.out.println("SelectionBehaviour: Datos combinados recibidos.");

            // Separar población y fitness
            String[] parts = serializedData.split("\\|");
            String serializedPopulation = parts[0];
            String serializedFitness = parts[1];

            // Deserializar datos
            List<double[]> deserializedPopulation = ArrayConverter.toArray(serializedPopulation);
            double[] b0 = deserializedPopulation.get(0);
            double[] b1 = deserializedPopulation.get(1);

            List<double[]> deserializedFitness = ArrayConverter.toArray(serializedFitness);
            double[] fitness = deserializedFitness.get(0);

            // Construir la matriz de población
            double[][] population = new double[b0.length][2];
            for (int i = 0; i < b0.length; i++) {
                population[i][0] = b0[i];
                population[i][1] = b1[i];
            }

            // Realizar selección
            double[][] selectedPopulation = RouletteSelection.rouletteSelection(population, fitness);

            // Serializar población seleccionada
            double[] selectedB0 = new double[selectedPopulation.length];
            double[] selectedB1 = new double[selectedPopulation.length];
            for (int i = 0; i < selectedPopulation.length; i++) {
                selectedB0[i] = selectedPopulation[i][0];
                selectedB1[i] = selectedPopulation[i][1];
            }
            String serializedSelection = ArrayConverter.toString(selectedB0, selectedB1);

            // Enviar datos al CrossoverAgent
            ACLMessage res = new ACLMessage(ACLMessage.REQUEST);
            res.addReceiver(myAgent.getAID(AgentsList.AGENT_CROSSOVER));
            res.setConversationId("SELECTION-DATA");
            res.setContent(serializedSelection);
            myAgent.send(res);

            // System.out.println("SelectionBehaviour: Selección realizada y enviada al CrossoverAgent.");
        } else {
            System.out.println("SelectionBehaviour: No se recibieron datos combinados dentro del tiempo esperado.");
        }
    }
}
