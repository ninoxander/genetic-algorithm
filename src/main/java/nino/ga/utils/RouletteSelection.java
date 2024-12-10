package nino.ga.utils;

import java.util.Random;

public class RouletteSelection {
    public static double[][] rouletteSelection(double[][] population, double[] fitness) {
        double totalFitness = 0;

        // Calcular el fitness total
        for (double f : fitness) {
            totalFitness += f;
        }

        // Calcular probabilidades
        double[] probabilities = new double[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            probabilities[i] = fitness[i] / totalFitness;
        }

        // Probabilidades acumulativas
        double[] cumulativeProbabilities = new double[fitness.length];
        cumulativeProbabilities[0] = probabilities[0];
        for (int i = 1; i < fitness.length; i++) {
            cumulativeProbabilities[i] = cumulativeProbabilities[i - 1] + probabilities[i];
        }

        // Selección
        double[][] selectedPopulation = new double[population.length][2];
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
