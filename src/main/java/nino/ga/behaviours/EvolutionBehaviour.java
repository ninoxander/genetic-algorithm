package nino.ga.behaviours;

import jade.core.behaviours.Behaviour;

public class EvolutionBehaviour extends Behaviour {
    private int maxGenerations;
    private int currentGeneration = 0;
    private double[][] population;
    private double[] fitness;
    private double[][] selectedPopulation;
    private double[][] newPopulation;
    private double mutationRate;
    private double crossoverRate;
    private double[] X;
    private double[] y;
    private double fitnessThreshold = 0.97;

    private PopulationBehaviour populationBehaviour;
    private FitnessBehaviour fitnessBehaviour;
    private SelectionBehaviour selectionBehaviour;
    private CrossoverBehaviour crossoverBehaviour;
    private MutationBehaviour mutationBehaviour;

    public EvolutionBehaviour(int maxGenerations, double mutationRate, double crossoverRate, double[] X, double[] y, int populationSize) {
        this.maxGenerations = maxGenerations;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.X = X;
        this.y = y;

        // Initialize behaviours
        this.populationBehaviour = new PopulationBehaviour(populationSize);
        this.fitnessBehaviour = new FitnessBehaviour();
        this.selectionBehaviour = new SelectionBehaviour();
        this.crossoverBehaviour = new CrossoverBehaviour(crossoverRate);
        this.mutationBehaviour = new MutationBehaviour(mutationRate);

        // Initialize population
        this.population = populationBehaviour.generateInitialPopulation();
    }

    @Override
    public void action() {
        fitness = fitnessBehaviour.calculateFitness(population, X, y);
        selectedPopulation = selectionBehaviour.select(population, fitness);
        newPopulation = crossoverBehaviour.performCrossover(selectedPopulation, fitness);
        population = mutationBehaviour.applyMutation(newPopulation);

        currentGeneration++;
    }

    @Override
    public boolean done() {
        if (currentGeneration >= maxGenerations) {
            System.out.println("\nEvolución completada tras " + maxGenerations + " generaciones.");

            // Calculate final results
            double[] bestCoefficients = getBestCoefficients();
            double rSquared = calculateRSquared(bestCoefficients, X, y);

            System.out.println("R^2 Final: " + rSquared);
            System.out.println("Valores finales de beta:");
            System.out.println("beta_0: " + bestCoefficients[0]);
            System.out.println("beta_1: " + bestCoefficients[1]);
            return true;
        }
        return false;
    }

    private double getBestFitness() {
        double bestFitness = Double.MIN_VALUE;
        for (double fit : fitness) {
            if (fit > bestFitness) {
                bestFitness = fit;
            }
        }
        return bestFitness;
    }

    private double[] getBestCoefficients() {
        double[] bestCoefficients = null;
        double bestFitness = Double.MAX_VALUE;

        for (double[] individual : population) {
            double fitnessValue = 1 / (1 + calculateError(individual, X, y));
            if (fitnessValue < bestFitness) {
                bestFitness = fitnessValue;
                bestCoefficients = individual.clone();
            }
        }

        return bestCoefficients;
    }

    private double calculateRSquared(double[] coefficients, double[] X, double[] y) {
        double ssTotal = 0.0;
        double ssResidual = 0.0;
        double yMean = calculateMean(y);

        for (int i = 0; i < y.length; i++) {
            double yPredicted = coefficients[0] + coefficients[1] * X[i];
            ssResidual += Math.pow(y[i] - yPredicted, 2);
            ssTotal += Math.pow(y[i] - yMean, 2);
        }

        return 1 - (ssResidual / ssTotal);
    }

    private double calculateError(double[] coefficients, double[] X, double[] y) {
        double error = 0.0;
        for (int i = 0; i < X.length; i++) {
            double prediction = coefficients[0] + coefficients[1] * X[i];
            error += Math.pow(y[i] - prediction, 2);
        }
        return error / X.length;
    }

    private double calculateMean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }
}
