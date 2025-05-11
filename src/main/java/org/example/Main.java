package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Path filePath = Path.of("src/main/resources/checksum - input.txt");
        String input = readInputFile(filePath);

        int checksum = 0;

        int digitToCompareOffset = 1;
        checksum = calculateChecksum(input, digitToCompareOffset);
        System.out.println("checksum with digit to compare at offset " + digitToCompareOffset + ": " + checksum);

        digitToCompareOffset = input.length() / 2;
        checksum = calculateChecksum(input, digitToCompareOffset);
        System.out.println("checksum with digit to compare at offset " + digitToCompareOffset + ": " + checksum);

        // -------------------------------------------------------------------------------------------------------------
        filePath = Path.of("src/main/resources/fractal - input.txt");
        List<String> inputLines = readInputFileByLine(filePath);
        calculateFractal(inputLines);
    }

    public static String readInputFile(Path filePath) {
        try {
            String input = Files.readString(filePath);
            System.out.println("input: " + input);
            return input;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the input data from file. Please provide the input file at the following path: " + filePath.toAbsolutePath(),e);
        }
    }

    public static List<String> readInputFileByLine(Path filePath) {
        try {
            List<String> input = Files.readAllLines(filePath);
            System.out.println("input: " + input);
            return input;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the input data from file. Please provide the input file at the following path: " + filePath.toAbsolutePath(),e);
        }
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
        String startPixelString = ".#./..#/###";
        Matrix startMatrix = Matrix.initialize(startPixelString);
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

        List<Matrix> grid = new LinkedList<>();
        grid.add(startMatrix);

        for (int iteration = 0; iteration < 20; iteration++) {
            List<Matrix> mutatedGrid = new LinkedList<>();
            for (Matrix matrix : grid) {
                Matrix extRuleReplaceMatrix = extensionRuleMap.get(matrix.toString());
                if (extRuleReplaceMatrix != null) {
                    if (extRuleReplaceMatrix.getSize() == 4) {
                        mutatedGrid.addAll(MatrixUtils.splitInFour(extRuleReplaceMatrix));
                    } else {
                        mutatedGrid.add(extRuleReplaceMatrix);
                    }
                } else {
                    mutatedGrid.add(matrix);
                }
            }
            grid = mutatedGrid;

            int numberOfPixelsOn = 0;
            for (Matrix matrix : grid) {
                numberOfPixelsOn += matrix.getNumberOfPixelsOn();
            }
            System.out.println("Iteration: " + (iteration + 1) + ", Pixels on: " + numberOfPixelsOn);


        }
    }
}
