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
        profile.setParameter(Profile.GUI, "false");

        ContainerController mainContainer = runtime.createMainContainer(profile);

        try {
            AgentController evolutionAgent = mainContainer.createNewAgent(
                    AgentsList.AGENT_EVOLUTION,
                    "nino.ga.agents.EvolutionAgent",
                    GenConfig.get()
            );

            evolutionAgent.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
