package nino.ga;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Main {
    public static void main(String[] args) {
        Runtime runtime = Runtime.instance();

        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");

        ContainerController mainContainer = runtime.createMainContainer(profile);

        try {
            AgentController populationAgent = mainContainer.createNewAgent(
                AgentsList.AGENT_POPULATION,
                "nino.ga.agents.PopulationAgent",
                null
            );
            AgentController fitnessAgent = mainContainer.createNewAgent(
                    AgentsList.AGENT_FITNESS,
                    "nino.ga.agents.FitnessAgent",
                    null
            );
            AgentController selectionAgent = mainContainer.createNewAgent(
                    AgentsList.AGENT_SELECTION,
                    "nino.ga.agents.SelectionAgent",
                    null
            );
            AgentController crossoverAgent = mainContainer.createNewAgent(
                    AgentsList.AGENT_CROSSOVER,
                    "nino.ga.agents.CrossoverAgent",
                    null
            );
            AgentController mutationAgent = mainContainer.createNewAgent(
                    AgentsList.AGENT_MUTATION,
                    "nino.ga.agents.MutationAgent",
                    null
            );
            AgentController evolutionAgent = mainContainer.createNewAgent(
                    AgentsList.AGENT_EVOLUTION,
                    "nino.ga.agents.EvolutionAgent",
                    null
            );

            evolutionAgent.start();
            populationAgent.start();
            fitnessAgent.start();
            selectionAgent.start();
            crossoverAgent.start();
            mutationAgent.start();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}