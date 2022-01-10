package ru.vsu.cs.kunakhova_a_y;

import ru.vsu.cs.kunakhova_a_y.utils.ArrayUtils;

import java.io.PrintStream;

public class ConsoleMain {
    public static class CmdParams {
        public String inputBigFile;
        public String inputSmallFile;
        public String outputResult;
        public boolean error;
        public boolean help;
        public boolean window;
    }

    public static CmdParams parseArgs(String[] args) {
        CmdParams params = new CmdParams();
        if (args.length > 0) {
            if (args[0].equals("--help")) {
                params.help = true;
                return params;
            }
            if (args[0].equals("--window")) {
                params.window = true;
                return params;
            }
            if (args.length > 1) {
                params.inputBigFile = args[0];
                params.inputSmallFile = args[1];
                params.outputResult = args[2];
                return params;
            }
        } else {
            params.help = true;
            params.error = true;
        }
        return params;
    }

    public static void main(String[] args) throws Exception {
        ConsoleMain.CmdParams params = parseArgs(args);
        if (params.help) {
            PrintStream out = params.error ? System.err : System.out;
            out.println("Usage:");
            out.println("  <cmd> <input-bigFile> <input-smallFile>");
            out.println("  for example: <cmd> f1.txt f2.txt f3.txt");
            out.println("  f1: bigFile f2: smallFile f3: outputFile");
            out.println("  <cmd> --help");
            out.println("  <cmd> --window  // show window");
            System.exit(params.error ? 1 : 0);
        }

        if (params.window) {
            GuiMain.winMain();
        } else {
            int[][] bigArr = ArrayUtils.readIntArray2FromFile(params.inputBigFile);
            if (bigArr == null) {
                System.err.printf("Can't read array from \"%s\"%n", params.inputBigFile);
                System.exit(2);

            }

            int[][] smallArr = ArrayUtils.readIntArray2FromFile(params.inputSmallFile);
            if (smallArr == null) {
                System.err.printf("Can't read array from \"%s\"%n", params.inputSmallFile);
                System.exit(2);

            }
            int[] result = ifSmallArrayInBigArray.solution(bigArr, smallArr);

            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i]);
                System.out.print("; ");
            }

            ArrayUtils.writeArrayToFile(params.outputResult, result);
        }
    }
}

