package ninoxit.gen.behaviours;

import java.util.Arrays;
import jade.core.behaviours.OneShotBehaviour;
import ninoxit.gen.src.GeneticAlgorithm;

public class GenBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        double[] X = {23, 26, 30, 34, 43, 48, 52, 57, 58};
        double[] y = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};

        GeneticAlgorithm ga = new GeneticAlgorithm(0.01, 100, 100, 0.95, 0, "SLR");

        double[][] population = ga.run(X, y);
        double[] bestCoefficients = ga.getBestCoefficients(population, X, y);

        System.out.println("Mejores coeficientes: " + Arrays.toString(bestCoefficients));
    }
}
