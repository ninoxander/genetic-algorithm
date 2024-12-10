package nino.ga.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import nino.ga.AgentsList;
import nino.ga.utils.ArrayConverter;

import java.util.Random;

public class PopulationBehaviour extends OneShotBehaviour {
    private int populationSize;

    public PopulationBehaviour(Agent agent, int populationSize) {
        super(agent);
        this.populationSize = populationSize;
    }

    @Override
    public void action() {
        // Esperar mensaje de inicio
        MessageTemplate template = MessageTemplate.MatchConversationId("POPULATION-INIT");
        ACLMessage initMsg = myAgent.blockingReceive(template);

        if (initMsg != null) {
            System.out.println("PopulationBehaviour: Mensaje de inicio recibido.");

            // Inicializar población
            Random random = new Random();
            double[][] population = new double[populationSize][2];
            for (int i = 0; i < populationSize; i++) {
                population[i][0] = random.nextDouble() * 15; // b0
                population[i][1] = random.nextDouble() * 28;  // b1
            }

            // Serializar población
            double[] b0 = new double[populationSize];
            double[] b1 = new double[populationSize];
            for (int i = 0; i < populationSize; i++) {
                b0[i] = population[i][0];
                b1[i] = population[i][1];
            }
            String serializedPopulation = ArrayConverter.toString(b0, b1);

            // Enviar población al FitnessAgent
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(myAgent.getAID(AgentsList.AGENT_FITNESS));
            msg.setConversationId("POPULATION-DATA");
            msg.setContent(serializedPopulation);
            myAgent.send(msg);

            // System.out.println("PopulationBehaviour: Población inicializada y enviada al FitnessAgent.");
        } else {
            System.out.println("PopulationBehaviour: No se recibió mensaje de inicio.");
        }
    }
}