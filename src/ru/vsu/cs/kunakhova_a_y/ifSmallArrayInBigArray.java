package ru.vsu.cs.kunakhova_a_y;

public class ifSmallArrayInBigArray {
    public static int[] solution(int[][] bigArr, int[][] smallArr) {
        int[] resultArr = new int[2];
        for (int i = 0; i <= bigArr.length - smallArr.length; i++) {
            for (int j = 0; j <= bigArr[0].length - smallArr[0].length; j++) {
                boolean okay = true;
                for (int k = 0; k < smallArr.length; k++) {
                    for (int l = 0; l < smallArr[0].length; l++) {
                        if (!(smallArr[k][l] == bigArr[i + k][j + l])) {
                            okay = false;
                            break;
                        }
                    }
                    if (!okay) {
                        break;
                    }
                }
                if (okay) {
                    resultArr[0] = i;
                    resultArr[1] = j;
                    return resultArr;
                }
            }
        }
        return null;
    }
}
