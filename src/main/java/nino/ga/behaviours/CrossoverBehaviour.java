package nino.ga.behaviours;

import nino.ga.utils.RouletteSelection;

import java.util.Random;

public class CrossoverBehaviour {
    private double crossoverRate;

    public CrossoverBehaviour(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public double[][] performCrossover(double[][] population, double[] fitness) {
        double[][] selectedPopulation = RouletteSelection.rouletteSelection(population, fitness);

        double[][] newPopulation = new double[selectedPopulation.length][selectedPopulation[0].length];
        Random rand = new Random();

        for (int i = 0; i < selectedPopulation.length; i += 2) {
            if (i + 1 < selectedPopulation.length && rand.nextDouble() < crossoverRate) {
                double[] parent1 = selectedPopulation[i];
                double[] parent2 = selectedPopulation[i + 1];

                double alpha = rand.nextDouble();
                newPopulation[i][0] = alpha * parent1[0] + (1 - alpha) * parent2[0];
                newPopulation[i][1] = alpha * parent1[1] + (1 - alpha) * parent2[1];
                newPopulation[i + 1][0] = alpha * parent2[0] + (1 - alpha) * parent1[0];
                newPopulation[i + 1][1] = alpha * parent2[1] + (1 - alpha) * parent1[1];
            } else {
                newPopulation[i] = selectedPopulation[i].clone();
                if (i + 1 < selectedPopulation.length) {
                    newPopulation[i + 1] = selectedPopulation[i + 1].clone();
                }
            }
        }

        return newPopulation;
    }
}
