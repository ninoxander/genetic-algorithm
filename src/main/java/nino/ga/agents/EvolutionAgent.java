package nino.ga.agents;

import jade.core.Agent;
import nino.ga.behaviours.EvolutionBehaviour;

public class EvolutionAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new EvolutionBehaviour(this, 10000));
    }
}
