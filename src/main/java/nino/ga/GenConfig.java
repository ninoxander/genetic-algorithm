package nino.ga;

public class GenConfig {
    public static int maxGenerations = 300;
    public static double mutationRate = 0.01;
    public static double crossoverRate = 0.90;
    public static double[] X = {23, 26, 30, 34, 43, 48, 52, 57, 58};
    public static double[] y = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};
    public static int populationSize = 300;

    public static Object[] get() {
        return new Object[] { maxGenerations, mutationRate, crossoverRate, X, y, populationSize };
    }
}
