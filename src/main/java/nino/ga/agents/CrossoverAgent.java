package nino.ga.agents;

import jade.core.Agent;

import nino.ga.behaviours.CrossoverBehaviour;

public class CrossoverAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CrossoverBehaviour(this));
    }
}
