package ru.vsu.cs.kunakhova_a_y.utils;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.io.PrintWriter;

public class ArrayUtils {

    /**
     * Измененная библиотека Дмитрия Соломатина (кафедра ПиИТ ФКН ВГУ)
     */

    private static final Random RND = new Random();

    public static int[] toPrimitive(Integer[] arr) {
        if (arr == null) {
            return null;
        }
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            // автоматическая распаковка из объекта
            result[i] = arr[i];
        }
        return result;
    }

    public static int[] toIntArray(String str) {
        Scanner scanner = new Scanner(str);
        scanner.useLocale(Locale.ROOT);
        scanner.useDelimiter("(\\s|[,;])+");
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.nextInt());
        }

        // из List<Integer> можно получить Integer[]
        Integer[] arr = list.toArray(new Integer[0]);
        // Integer[] -> int[]
        return ArrayUtils.toPrimitive(arr);
    }

    public static String toString(int[] arr, String itemFormat) {
        if (arr == null) {
            return null;
        }
        if (itemFormat == null || itemFormat.length() == 0) {
            itemFormat = "%s";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(String.format(Locale.ROOT, itemFormat, arr[i]));
        }
        return sb.toString();
    }

    /**
     * Конвертация массива строк в двухмерный массив чисел int[][]
     */
    public static int[][] toIntArray2(String[] lines) {
        int[][] arr2 = new int[lines.length][];
        for (int r = 0; r < lines.length; r++) {
            arr2[r] = toIntArray(lines[r]);
        }
        return arr2;
    }

    /**
     * Чтение двухмерного массива int[][] с консоли;
     * checkMatrix - задает режим, при котором,
     * если строки содержат разное кол-во элементот, то
     * это считается ошибкой и предлагается повторить ввод
     */


    public static String toString(int[][] arr2, String itemFormat) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < arr2.length; r++) {
            if (r > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(toString(arr2[r], itemFormat));
        }
        return sb.toString();
    }

    /**
     * Чтение всего текстового файла в массив строк
     */
    public static String[] readLinesFromFile(String fileName) throws FileNotFoundException {
        List<String> lines;
        try (Scanner scanner = new Scanner(new File(fileName), "UTF-8")) {
            lines = new ArrayList<>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
            // обязательно, чтобы закрыть открытый файл
        }
        return lines.toArray(new String[0]);
    }

    /**
     * Чтение массива int[] из первой строки текстового файла
     */
    public static int[] readIntArrayFromFile(String fileName) {
        try {
            return toIntArray(readLinesFromFile(fileName)[0]);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Чтение двухмерного массива из текстового файла
     */
    public static int[][] readIntArray2FromFile(String fileName) {
        try {
            return toIntArray2(readLinesFromFile(fileName));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static void writeArrayToFile(String fileName, int[] arr, String itemFormat)
            throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(toString(arr, itemFormat));
        }
    }

    public static void writeArrayToFile(String fileName, int[] arr)
            throws FileNotFoundException {
        writeArrayToFile(fileName, arr, null);
    }

    public static void writeArrayToFile(String fileName, int[][] arr2, String itemFormat)
            throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(toString(arr2, itemFormat));
        }
    }

    public static void writeArrayToFile(String fileName, int[][] arr2)
            throws FileNotFoundException {
        writeArrayToFile(fileName, arr2, null);
    }

    /**
     * Cоздание одномерного массива целых чисел, заполненного случайными числами
     *
     * @param length   Кол-во элементов в массиве
     * @param minValue Минимальное значение для случайных чисел (включая)
     * @param maxValue Максимальное значение (не включая)
     * @return Массив int[]
     */
    public static int[] createRandomIntArray(int length, int minValue, int maxValue) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++)
            arr[i] = minValue + RND.nextInt(maxValue - minValue);
        return arr;
    }

    /**
     * Cоздание двухмерного массива целых чисел, заполненного случайными числами
     *
     * @param rowCount Кол-во сток в двумерном массиве
     * @param colCount Кол-во столбцов (элементов в каждой строке)
     * @param minValue Минимальное значение для случайных чисел (включая)
     * @param maxValue Максимальное значение (не включая)
     * @return Массив int[][]
     */
    public static int[][] createRandomIntMatrix(int rowCount, int colCount, int minValue, int maxValue) {
        int[][] matrix = new int[rowCount][];
        for (int r = 0; r < rowCount; r++)
            matrix[r] = createRandomIntArray(colCount, minValue, maxValue);
        return matrix;
    }

    /**
     * @see #createRandomIntMatrix(int, int, int, int)
     */
    public static int[][] createRandomIntMatrix(int rowCount, int colCount, int maxValue) {
        return createRandomIntMatrix(rowCount, colCount, 0, maxValue);
    }

}
