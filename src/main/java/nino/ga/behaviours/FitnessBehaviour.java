package nino.ga.behaviours;

public class FitnessBehaviour {
    public double[] calculateFitness(double[][] population, double[] X, double[] y) {
        double[] fitness = new double[population.length];
        for (int i = 0; i < population.length; i++) {
            double error = 0.0;
            for (int j = 0; j < X.length; j++) {
                double prediction = population[i][0] + population[i][1] * X[j];
                error += Math.pow(y[j] - prediction, 2);
            }
            fitness[i] = 1 / (1 + error);
        }
        return fitness;
    }
}
