package discretemaths;

public class DiscreteMaths {
    public static double sum(double[] numbers) {
        double total = 0.0;
        for (double num : numbers) {
            total += num;
        }
        return total;
    }

    public static double mean(double[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("El arreglo no puede estar vacío.");
        }
        return sum(numbers) / numbers.length;
    }

    public static double weightedSum(double[] array1, double[] array2) {
        if (array1.length != array2.length) {
            throw new IllegalArgumentException("Los arreglos deben tener el mismo tamaño.");
        }
        double total = 0.0;
        for (int i = 0; i < array1.length; i++) {
            total += array1[i] * array2[i];
        }
        return total;
    }

    public static double[] calculateProbabilities(double[] fitnesses) {
        double totalFitness = sum(fitnesses);
        double[] probabilities = new double[fitnesses.length];
        for (int i = 0; i < fitnesses.length; i++) {
            probabilities[i] = fitnesses[i] / totalFitness;
        }
        return probabilities;
    }

    public static double[] power(double[] numbers, int exponent) {
        double[] powered = new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            powered[i] = Math.pow(numbers[i], exponent);
        }
        return powered;
    }

    public static double squaredErrorSum(double[] actual, double[] predicted) {
        if (actual.length != predicted.length) {
            throw new IllegalArgumentException("Los arreglos deben tener el mismo tamaño.");
        }
        double total = 0.0;
        for (int i = 0; i < actual.length; i++) {
            total += Math.pow(actual[i] - predicted[i], 2);
        }
        return total;
    }
}