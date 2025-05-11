package org.example;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Path filePath = Path.of("src/main/resources/checksum - input.txt");
        String input = FileUtils.readInputFile(filePath);

        int checksum = 0;

        int digitToCompareOffset = 1;
        checksum = calculateChecksum(input, digitToCompareOffset);
        System.out.println("checksum with digit to compare at offset " + digitToCompareOffset + ": " + checksum);

        digitToCompareOffset = input.length() / 2;
        checksum = calculateChecksum(input, digitToCompareOffset);
        System.out.println("checksum with digit to compare at offset " + digitToCompareOffset + ": " + checksum);

        // -------------------------------------------------------------------------------------------------------------
        filePath = Path.of("src/main/resources/fractal - input.txt");
        List<String> inputLines = FileUtils.readInputFileByLine(filePath);
        calculateFractal(inputLines);
    }

    public static int calculateChecksum(String input, int digitToCompareOffset) {
        int currentDigit, compareDigitIndex, compareDigit, checksum = 0;
        for (int currentDigitIndex = 0; currentDigitIndex < input.length(); currentDigitIndex++) {
            currentDigit = Integer.parseInt(String.valueOf(input.charAt(currentDigitIndex)));
            compareDigitIndex = (currentDigitIndex + digitToCompareOffset) % input.length();
            compareDigit = Integer.parseInt(String.valueOf(input.charAt(compareDigitIndex)));
            if (compareDigit == currentDigit) {
                checksum += currentDigit;
            }
        }
        return checksum;
    }

    public static void calculateFractal(List<String> input) {
        Map<String, Matrix> extensionRuleMap = new HashMap<>();

        for (String inputLine : input) {
            String[] extensionRule = inputLine.split(" => ");

            Matrix extRuleMatchMatrix = Matrix.initialize(extensionRule[0]);
            Matrix extRuleReplaceMatrix = Matrix.initialize(extensionRule[1]);

            extensionRuleMap.put(extRuleMatchMatrix.toString(), extRuleReplaceMatrix);
            extensionRuleMap.put(MatrixUtils.mirrorHorizontal(extRuleMatchMatrix).toString(), extRuleReplaceMatrix);
            extensionRuleMap.put(MatrixUtils.mirrorVertical(extRuleMatchMatrix).toString(), extRuleReplaceMatrix);

            extRuleMatchMatrix = MatrixUtils.rotate90ClockWise(extRuleMatchMatrix);
            extensionRuleMap.put(extRuleMatchMatrix.toString(), extRuleReplaceMatrix);
            extensionRuleMap.put(MatrixUtils.mirrorHorizontal(extRuleMatchMatrix).toString(), extRuleReplaceMatrix);
            extensionRuleMap.put(MatrixUtils.mirrorVertical(extRuleMatchMatrix).toString(), extRuleReplaceMatrix);

            extRuleMatchMatrix = MatrixUtils.rotate90ClockWise(extRuleMatchMatrix);
            extensionRuleMap.put(extRuleMatchMatrix.toString(), extRuleReplaceMatrix);
            extensionRuleMap.put(MatrixUtils.mirrorHorizontal(extRuleMatchMatrix).toString(), extRuleReplaceMatrix);
            extensionRuleMap.put(MatrixUtils.mirrorVertical(extRuleMatchMatrix).toString(), extRuleReplaceMatrix);

            extRuleMatchMatrix = MatrixUtils.rotate90ClockWise(extRuleMatchMatrix);
            extensionRuleMap.put(extRuleMatchMatrix.toString(), extRuleReplaceMatrix);
            extensionRuleMap.put(MatrixUtils.mirrorHorizontal(extRuleMatchMatrix).toString(), extRuleReplaceMatrix);
            extensionRuleMap.put(MatrixUtils.mirrorVertical(extRuleMatchMatrix).toString(), extRuleReplaceMatrix);
        }

        String startPixelString = ".#./..#/###";
        Matrix currentMatrix = Matrix.initialize(startPixelString);

        for (int iteration = 0; iteration < 20; iteration++) {
            Map<RowColIndex, Matrix> matrixGrid = new HashMap<>();
            if (currentMatrix.getSize() % 2 == 0) {
                matrixGrid = MatrixUtils.splitIn2x2(currentMatrix);
            }

            if (currentMatrix.getSize() % 3 == 0) {
                matrixGrid = MatrixUtils.splitIn3x3(currentMatrix);
            }

            for (RowColIndex rowColIndex : matrixGrid.keySet()) {
                Matrix extRuleReplaceMatrix = extensionRuleMap.get(matrixGrid.get(rowColIndex).toString());
                if (extRuleReplaceMatrix != null) {
                    matrixGrid.put(rowColIndex, extRuleReplaceMatrix);
                }
            }

            currentMatrix = MatrixUtils.mergeMatrices(matrixGrid);

            System.out.println("Iteration: " + (iteration + 1) + ", Pixels on: " + currentMatrix.getNumberOfPixelsOn());
        }
    }
}
