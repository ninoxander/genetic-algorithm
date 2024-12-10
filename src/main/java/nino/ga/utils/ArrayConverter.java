package nino.ga.utils;
/*
 * Daniela Ivette Nava Miranda
 * Contributor: han
 */
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class ArrayConverter {
    public static String toString(double[] a, double[] b){
        String joinedA = Arrays.toString(a).replace("[", "").replace("]", "").replace(" ", "");
        String joinedB = Arrays.toString(b).replace("[", "").replace("]", "").replace(" ", "");

        return joinedA + ";" + joinedB;
    }

    public static List<double[]> toArray(String str) {
        String[] parts = str.split(";");
        double[] array1 = parts.length > 0 ? Arrays.stream(parts[0].split(","))
                .mapToDouble(Double::parseDouble).toArray() : new double[0];
        double[] array2 = parts.length > 1 ? Arrays.stream(parts[1].split(","))
                .mapToDouble(Double::parseDouble).toArray() : new double[0];
        List<double[]> result = new ArrayList<>();
        result.add(array1);
        result.add(array2);
        return result;
    }
}
