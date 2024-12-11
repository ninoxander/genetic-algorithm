package nino.ga.utils;

import java.util.Random;

public class RouletteSelection {
    public static double[][] rouletteSelection(double[][] population, double[] fitness) {
        double totalFitness = 0;

        for (double f : fitness) {
            totalFitness += f;
        }

        if (totalFitness == 0) {
            throw new IllegalArgumentException("El fitness es 0, es necesario cambiar los valores de entrada.");
        }

        double[] cumulativeProbabilities = new double[fitness.length];
        cumulativeProbabilities[0] = fitness[0] / totalFitness;
        for (int i = 1; i < fitness.length; i++) {
            cumulativeProbabilities[i] = cumulativeProbabilities[i - 1] + (fitness[i] / totalFitness);
        }

        // Selección
        double[][] selectedPopulation = new double[population.length][population[0].length];
        Random random = new Random();
        for (int i = 0; i < population.length; i++) {
            double r = random.nextDouble();
            for (int j = 0; j < cumulativeProbabilities.length; j++) {
                if (r <= cumulativeProbabilities[j]) {
                    selectedPopulation[i] = population[j].clone();
                    break;
                }
            }
        }

        return selectedPopulation;
    }
}
