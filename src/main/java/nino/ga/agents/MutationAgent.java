package nino.ga.agents;

import jade.core.Agent;
import nino.ga.behaviours.MutationBehaviour;

public class MutationAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new MutationBehaviour(this));
    }
}
