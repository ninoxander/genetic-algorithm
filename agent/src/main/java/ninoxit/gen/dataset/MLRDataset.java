package Datasets;

public class MLRDataset {
    private double[][] X;  // Variables independientes
    private double[] Y;    // Variable dependiente

    // Constructor
    public MLRDataset(double[][] X, double[] Y) {
        if (X.length != Y.length) {
            throw new IllegalArgumentException("El número de muestras en X y Y debe ser igual.");
        }
        this.X = X;
        this.Y = Y;
    }

    // Obtener la matriz de características X
    public double[][] getX() {
        return X;
    }

    // Obtener el vector Y
    public double[] getY() {
        return Y;
    }

    // Método para mostrar los datos
    public void printData() {
        for (int i = 0; i < X.length; i++) {
            System.out.print("X: ");
            for (int j = 0; j < X[i].length; j++) {
                System.out.print(X[i][j] + " ");
            }
            System.out.println(", Y: " + Y[i]);
        }
    }
}
