package ru.job4j.pools;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenMatrix2x2() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[2][2];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[1][0] = 3;
        matrix[1][1] = 4;
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        int expected = 7;
        int out = sums[1].getRowSum();
        Assert.assertEquals(expected, out);

    }

    @Test
    public void whenMatrix3x3() throws ExecutionException, InterruptedException {
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
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix2);
        assertThat(sums[0].getColSum(), is(12));
    }
}