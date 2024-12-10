package nino.ga.agents;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import nino.ga.behaviours.FitnessBehaviour;

public class FitnessAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new FitnessBehaviour(this));
    }
}
