package nino.ga.agents;

import jade.core.Agent;
import nino.ga.behaviours.SelectionBehaviour;

public class SelectionAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new SelectionBehaviour(this));
    }
}
