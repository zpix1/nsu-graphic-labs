package fit.g19202.baksheev.lab5.lib;

import lombok.Getter;

import java.io.Serializable;

public class Matrix implements Serializable {
    @Getter
    private final int M;
    @Getter
    private final int N;
    private final double[][] data;

    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, N);
        }
    }

    /**
     * Creates a vector (horizontal)
     *
     * @param data vector to use
     */
    public Matrix(double[] data) {
        M = 1;
        N = data.length;
        this.data = new double[M][N];
        System.arraycopy(data, 0, this.data[0], 0, N);
    }

    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    public Matrix transpose() {
        var A = new Matrix(N, M);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                A.data[j][i] = this.data[i][j];
            }
        }
        return A;
    }

    public Matrix times(Matrix B) {
        var A = this;
        if (A.N != B.M) {
            throw new RuntimeException("Illegal matrix dimensions: " + A.N + " != " + B.M);
        }
        var C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++) {
            for (int j = 0; j < C.N; j++) {
                for (int k = 0; k < A.N; k++) {
                    C.data[i][j] += A.data[i][k] * B.data[k][j];
                }
            }
        }
        return C;
    }

    public Vec4 times(Vec4 b) {
        if (N != 4) {
            throw new RuntimeException("Illegal matrix dimensions: " + N + " != " + 4);
        }
        var x = data[0][0] * b.getX() + data[1][0] * b.getY() + data[2][0] * b.getZ() + data[3][0];
        var y = data[0][1] * b.getX() + data[1][1] * b.getY() + data[2][1] * b.getZ() + data[3][1];
        var z = data[0][2] * b.getX() + data[1][2] * b.getY() + data[2][2] * b.getZ() + data[3][2];
        var w = data[0][3] * b.getX() + data[1][3] * b.getY() + data[2][3] * b.getZ() + data[3][3];
        if (w != 0) {
            x /= w;
            y /= w;
            z /= w;
        }
        return new Vec4(x, y, z);
    }

    public double get(int i, int j) {
        return data[i][j];
    }

    public void show() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%5.2f ", data[i][j]);
            }
            System.out.println();
        }
    }
}
