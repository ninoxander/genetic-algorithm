package nino.ga.behaviours;

import java.util.Random;

public class SelectionBehaviour {
    public double[][] select(double[][] population, double[] fitness) {
        double[][] selectedPopulation = new double[population.length][population[0].length];
        double[] probabilities = calculateProbabilities(fitness);
        Random rand = new Random();

        for (int i = 0; i < population.length; i++) {
            double r = rand.nextDouble();
            double cumulativeProbability = 0.0;

            for (int j = 0; j < probabilities.length; j++) {
                cumulativeProbability += probabilities[j];
                if (r <= cumulativeProbability) {
                    selectedPopulation[i] = population[j].clone();
                    break;
                }
            }
        }

        return selectedPopulation;
    }

    private double[] calculateProbabilities(double[] fitness) {
        double totalFitness = 0.0;
        for (double f : fitness) {
            totalFitness += f;
        }

        double[] probabilities = new double[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            probabilities[i] = fitness[i] / totalFitness;
        }

        return probabilities;
    }
}
