package ru.vsu.cs.kunakhova_a_y;

import org.junit.Assert;
import org.junit.Test;
import ru.vsu.cs.kunakhova_a_y.utils.ArrayUtils;

public class ifSmallArrayInBigArrayTest {

    @Test
    public void testRandom1() {
        int[] correctResult = ArrayUtils.readIntArrayFromFile("./testFiles/answer(1).txt");
        int[][] arrayBig = ArrayUtils.readIntArray2FromFile("./testFiles/testBigArray(1).txt");
        int[][] arraySmall = ArrayUtils.readIntArray2FromFile("./testFiles/testSmallArray(1).txt");

        assert (arrayBig != null) && (arraySmall != null);
        int[] currentResult = ifSmallArrayInBigArray.solution(arrayBig, arraySmall);

        Assert.assertArrayEquals(currentResult, correctResult);
    }

    @Test
    public void testUpperLeftCorner2() {
        int[] correctResult = ArrayUtils.readIntArrayFromFile("./testFiles/answer(2).txt");
        int[][] arrayBig = ArrayUtils.readIntArray2FromFile("./testFiles/testBigArray(2).txt");
        int[][] arraySmall = ArrayUtils.readIntArray2FromFile("./testFiles/testSmallArray(2).txt");

        assert (arrayBig != null) && (arraySmall != null);
        int[] currentResult = ifSmallArrayInBigArray.solution(arrayBig, arraySmall);

        Assert.assertArrayEquals(currentResult, correctResult);
    }

    @Test
    public void testUpperRightCorner3() {
        int[] correctResult = ArrayUtils.readIntArrayFromFile("./testFiles/answer(3).txt");
        int[][] arrayBig = ArrayUtils.readIntArray2FromFile("./testFiles/testBigArray(3).txt");
        int[][] arraySmall = ArrayUtils.readIntArray2FromFile("./testFiles/testSmallArray(3).txt");

        assert (arrayBig != null) && (arraySmall != null);
        int[] currentResult = ifSmallArrayInBigArray.solution(arrayBig, arraySmall);

        Assert.assertArrayEquals(currentResult, correctResult);
    }

    @Test
    public void testMiddle4() {
        int[] correctResult = ArrayUtils.readIntArrayFromFile("./testFiles/answer(4).txt");
        int[][] arrayBig = ArrayUtils.readIntArray2FromFile("./testFiles/testBigArray(4).txt");
        int[][] arraySmall = ArrayUtils.readIntArray2FromFile("./testFiles/testSmallArray(4).txt");

        assert (arrayBig != null) && (arraySmall != null);
        int[] currentResult = ifSmallArrayInBigArray.solution(arrayBig, arraySmall);

        Assert.assertArrayEquals(currentResult, correctResult);
    }

    @Test
    public void testHorizontalLine5() {
        int[] correctResult = ArrayUtils.readIntArrayFromFile("./testFiles/answer(5).txt");
        int[][] arrayBig = ArrayUtils.readIntArray2FromFile("./testFiles/testBigArray(5).txt");
        int[][] arraySmall = ArrayUtils.readIntArray2FromFile("./testFiles/testSmallArray(5).txt");

        assert (arrayBig != null) && (arraySmall != null);
        int[] currentResult = ifSmallArrayInBigArray.solution(arrayBig, arraySmall);

        Assert.assertArrayEquals(currentResult, correctResult);
    }
}
