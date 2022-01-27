package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = new Sums(
                    getRowSum(matrix, 0, n - 1, i),
                    getColSum(matrix, 0, n - 1, i)
            );
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < n; i++) {
            futures.put(i, getTask(matrix, 0, n - 1, i));
            if (i < n - 1) {
                futures.put(n - 1, getTask(matrix, 0, i, i));
            }
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int value1, int value2, int value3) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums(
                    getRowSum(data, value1, value2, value3),
                    getColSum(data, value1, value2, value3)
            );
            return sum;
        });
    }

    private static int getRowSum(int[][] data, int startRow, int endRow, int startCol) {
        int sum = 0;
        int col = startCol;
        for (int i = startRow; i <= endRow; i++) {
            sum += data[col][i];
        }
        return sum;
    }

    private static int getColSum(int[][] data, int startCol, int endCol, int startRow) {
        int sum = 0;
        int row = startRow;
        for (int i = startCol; i <= endCol; i++) {
            sum += data[i][row];
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix2 = new int[3][3];
        matrix2[0][0] = 1;
        matrix2[0][1] = 2;
        matrix2[0][2] = 3;
        matrix2[1][0] = 4;
        matrix2[1][1] = 5;
        matrix2[1][2] = 6;
        matrix2[2][0] = 7;
        matrix2[2][1] = 8;
        matrix2[2][2] = 9;
        long millis = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            sum(matrix2);
            System.out.println(i);
        }
        long current = System.currentTimeMillis();
        long diff = current - millis;
        System.out.println("Время выполнения последовательной версии: " + diff);
        Thread.sleep(3000);
        millis = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            asyncSum(matrix2);
            System.out.println(i);
        }
        current = System.currentTimeMillis();
        diff = current - millis;
        System.out.println("Время выполнения асинхронной версии: " + diff);
    }
}
