package ninoxit.gen.src;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {

    private double mutationRate;
    private int populationSize;
    private int numGenerations;
    private double crossoverRate;
    private int elitismCount;
    private String modelType;

    public GeneticAlgorithm(double mutationRate, int populationSize, int numGenerations, double crossoverRate, int elitismCount, String modelType) {
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.numGenerations = numGenerations;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.modelType = modelType;
    }

    public double evaluateFitness(double[] coefficients, double[] X, double[] y) {
        double error = 0.0;
        if ("SLR".equals(modelType)) {
            for (int i = 0; i < X.length; i++) {
                double prediction = coefficients[0] + coefficients[1] * X[i];
                error += Math.pow(y[i] - prediction, 2);
            }
        } else if ("MLR".equals(modelType)) {
            for (int i = 0; i < X.length; i++) {
                double prediction = coefficients[0];
                for (int j = 1; j < coefficients.length; j++) {
                    prediction += coefficients[j] * X[i + j - 1]; // Multiple X terms for MLR
                }
                error += Math.pow(y[i] - prediction, 2);
            }
        } else if ("Poly".equals(modelType)) {
            for (int i = 0; i < X.length; i++) {
                double prediction = coefficients[0];
                for (int j = 1; j < coefficients.length; j++) {
                    prediction += coefficients[j] * Math.pow(X[i], j); // Polynomial regression term
                }
                error += Math.pow(y[i] - prediction, 2);
            }
        }
        return error / X.length;
    }

    public double[][] crossover(double[][] population, double[] fitness) {
        int n = population.length;
        int m = population[0].length;
        double[][] newPopulation = new double[n][m];
        Random rand = new Random();

        for (int i = elitismCount; i < n; i++) {
            if (rand.nextDouble() < crossoverRate) {
                int parent1Index = RouletteWheelSelection.select(fitness);
                int parent2Index = RouletteWheelSelection.select(fitness);
                int crossoverPoint = rand.nextInt(m);

                for (int j = 0; j < m; j++) {
                    if (j < crossoverPoint) {
                        newPopulation[i][j] = population[parent1Index][j];
                    } else {
                        newPopulation[i][j] = population[parent2Index][j];
                    }
                }
            } else {
                newPopulation[i] = Arrays.copyOf(population[i], m);
            }
        }

        return newPopulation;
    }

    public void mutate(double[][] population) {
        Random rand = new Random();
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[i].length; j++) {
                if (rand.nextDouble() < mutationRate) {
                    population[i][j] += rand.nextGaussian();
                }
            }
        }
    }

    public double[][] initializePopulation(int numIndividuals, int numGenes) {
        Random rand = new Random();
        double[][] population = new double[numIndividuals][numGenes];
        for (int i = 0; i < numIndividuals; i++) {
            for (int j = 0; j < numGenes; j++) {
                population[i][j] = rand.nextDouble();
            }
        }
        return population;
    }

    public double[][] run(double[] X, double[] y) {
        int numGenes = "SLR".equals(modelType) ? 2 : "MLR".equals(modelType) ? X.length + 1 : X.length + 1;
        double[][] population = initializePopulation(populationSize, numGenes);

        for (int generation = 0; generation < numGenerations; generation++) {
            double[] fitness = new double[population.length];

            for (int i = 0; i < population.length; i++) {
                fitness[i] = evaluateFitness(population[i], X, y);
            }

            population = crossover(population, fitness);

            mutate(population);

            double bestFitness = Arrays.stream(fitness).min().getAsDouble();
            System.out.println("Generación " + generation + ": Mejor Fitness = " + bestFitness);
        }

        return population;
    }

    public double[] getBestCoefficients(double[][] population, double[] X, double[] y) {
        double[] bestCoefficients = null;
        double bestFitness = Double.MAX_VALUE;

        for (double[] coefficients : population) {
            double fitness = evaluateFitness(coefficients, X, y);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestCoefficients = coefficients;
            }
        }

        return bestCoefficients;
    }
}