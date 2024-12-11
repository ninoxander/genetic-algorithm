package nino.ga.agents;

import jade.core.Agent;
import nino.ga.behaviours.EvolutionBehaviour;

public class EvolutionAgent extends Agent {
    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length == 6) {
            int maxGenerations = (int) args[0];
            double mutationRate = (double) args[1];
            double crossoverRate = (double) args[2];
            double[] X = (double[]) args[3];
            double[] y = (double[]) args[4];
            int populationSize = (int) args[5];
            addBehaviour(new EvolutionBehaviour(maxGenerations, mutationRate, crossoverRate, X, y, populationSize));
        } else {
            System.err.println("EvolutionAgent: Argumentos inválidos o incompletos.");
            doDelete();
        }
    }
}
