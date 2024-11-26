package ninoxit.gen;


import jade.core.Agent;
import ninoxit.gen.behaviours.GenBehaviour;

public class GenAgent extends Agent {
    @Override
    public void setup() {
        addBehaviour(new GenBehaviour());
    }
}