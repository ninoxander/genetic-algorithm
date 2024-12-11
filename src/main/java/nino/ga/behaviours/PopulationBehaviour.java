package nino.ga.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import nino.ga.utils.ArrayConverter;

import java.util.Random;

public class PopulationBehaviour {
    private int populationSize;

    public PopulationBehaviour(int populationSize) {
        this.populationSize = populationSize;
    }

    public double[][] generateInitialPopulation() {
        double[][] population = new double[populationSize][2];
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            population[i][0] = random.nextDouble() * 200;
            population[i][1] = random.nextDouble() * 30;
        }
        return population;
    }
}
