package ninoxit.gen.discretemaths;

public class MatrixOperations extends DiscreteMaths {

    // Método para transponer una matriz
    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposed = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    // Método para multiplicar dos matrices
    public static double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return result;
    }

    // Método para calcular la inversa de una matriz usando eliminación gaussiana
    public static double[][] invert(double[][] matrix) {
        int n = matrix.length;
        double[][] augmented = new double[n][2 * n];

        // Crear la matriz aumentada [A | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
            }
            augmented[i][i + n] = 1.0;
        }

        // Eliminación Gaussiana
        for (int i = 0; i < n; i++) {
            // Escalar la fila para que el pivote sea 1
            double pivot = augmented[i][i];
            if (pivot == 0) {
                throw new IllegalArgumentException("La matriz no es invertible.");
            }
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivot;
            }

            // Hacer ceros en las demás filas de la columna del pivote
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }

        // Extraer la matriz inversa
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmented[i][j + n];
            }
        }
        return inverse;
    }

    // Método para multiplicar una matriz por un vector
    public static double[] multiply(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    // Método para calcular la suma de los elementos de una matriz usando DiscreteMaths
    public static double sumMatrix(double[][] matrix) {
        double totalSum = 0.0;
        for (double[] row : matrix) {
            totalSum += sum(row); // Usa el método sum de DiscreteMaths
        }
        return totalSum;
    }

    // Método para calcular el promedio de los elementos de una matriz usando DiscreteMaths
    public static double meanMatrix(double[][] matrix) {
        double totalSum = sumMatrix(matrix);
        int totalElements = matrix.length * matrix[0].length;
        return totalSum / totalElements;
    }
}
