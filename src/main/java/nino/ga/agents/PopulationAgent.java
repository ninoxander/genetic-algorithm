package nino.ga.agents;

import jade.core.Agent;
import nino.ga.GenConfig;
import nino.ga.behaviours.PopulationBehaviour;

public class PopulationAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new PopulationBehaviour(this, GenConfig.POPULATION_SIZE));
    }
}
