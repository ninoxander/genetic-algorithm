package nino.ga.behaviours;

import java.util.Random;

public class MutationBehaviour {
    private double mutationRate;

    public MutationBehaviour(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public double[][] applyMutation(double[][] population) {
        Random rand = new Random();

        for (int i = 0; i < population.length; i++) {
            if (rand.nextDouble() < mutationRate) {
                int selectedGen = rand.nextInt(population[i].length);
                double mutationValue = rand.nextGaussian() * 0.2;

                population[i][selectedGen] += mutationValue;
            }

        }

        return population;
    }
}
