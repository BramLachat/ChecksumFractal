package org.example;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // +----------------------+
        // | EXERCISE 1: CHECKSUM |
        // +----------------------+
        Path filePath = Path.of("src/main/resources/checksum - input.txt");
        String input = FileUtils.readInputFile(filePath);

        int checksum = 0;

        // 1.1 Compare each digit with the next digit
        int digitToCompareOffset = 1;
        checksum = calculateChecksum(input, digitToCompareOffset);
        System.out.println("Checksum with digit to compare at offset " + digitToCompareOffset + ": " + checksum);

        // 1.2 Compare each digit with the digit halfway down the ring buffer
        digitToCompareOffset = input.length() / 2;
        checksum = calculateChecksum(input, digitToCompareOffset);
        System.out.println("Checksum with digit to compare at offset " + digitToCompareOffset + ": " + checksum);

        // +---------------------+
        // | EXERCISE 2: FRACTAL |
        // +---------------------+
        filePath = Path.of("src/main/resources/fractal - input.txt");
        List<String> transformationRules = FileUtils.readInputFileByLine(filePath);
        calculateFractal(transformationRules);
    }

    private static int calculateChecksum(String input, int digitToCompareOffset) {
        int currentDigit, compareDigitIndex, compareDigit, checksum = 0;
        for (int currentDigitIndex = 0; currentDigitIndex < input.length(); currentDigitIndex++) {
            currentDigit = Integer.parseInt(String.valueOf(input.charAt(currentDigitIndex)));
            // Modulo operator to make sure the comparison starts again at the beginning when the end is reached.
            // This will make it behave like a ring buffer.
            compareDigitIndex = (currentDigitIndex + digitToCompareOffset) % input.length();
            compareDigit = Integer.parseInt(String.valueOf(input.charAt(compareDigitIndex)));
            if (compareDigit == currentDigit) {
                checksum += currentDigit;
            }
        }
        return checksum;
    }

    private static void calculateFractal(List<String> transformationRules) {
        // Store all transformations inside this map to prevent
        // that the same rotation and mirror operations must be calculated multiple times.
        Map<String, Matrix> transformationRuleMap = new HashMap<>();

        for (String transformationRule : transformationRules) {
            String[] transformationRuleParts = transformationRule.split(" => ");

            Matrix transRuleMatchMatrix = new Matrix(transformationRuleParts[0]);
            Matrix transRuleReplaceMatrix = new Matrix(transformationRuleParts[1]);

            storeTransformationInMap(transformationRuleMap, transRuleMatchMatrix, transRuleReplaceMatrix);

            transRuleMatchMatrix = MatrixUtils.rotate90ClockWise(transRuleMatchMatrix);
            storeTransformationInMap(transformationRuleMap, transRuleMatchMatrix, transRuleReplaceMatrix);

            transRuleMatchMatrix = MatrixUtils.rotate90ClockWise(transRuleMatchMatrix);
            storeTransformationInMap(transformationRuleMap, transRuleMatchMatrix, transRuleReplaceMatrix);

            transRuleMatchMatrix = MatrixUtils.rotate90ClockWise(transRuleMatchMatrix);
            storeTransformationInMap(transformationRuleMap, transRuleMatchMatrix, transRuleReplaceMatrix);
        }

        String startPixelString = ".#./..#/###";
        // currentMatrix: Stores all the pixels that are on or off in the square grid.
        Matrix currentMatrix = new Matrix(startPixelString);

        for (int iteration = 0; iteration < 18; iteration++) {
            // splitMatrixGrid: Contains all the split matrices.
            // Key of first map represents the row index (x).
            // Key of second map represents the column index (y).
            // Value of second map contains the split matrix for row (x) and column (y).
            Map<Integer, Map<Integer, Matrix>> splitMatrixGrid = null;

            // Check that the entire grid is divisible by 2
            if (currentMatrix.getSize() % 2 == 0) {
                splitMatrixGrid = MatrixUtils.splitIn2x2(currentMatrix);

            // Check that the entire grid is divisible by 3
            } else if (currentMatrix.getSize() % 3 == 0) {
                splitMatrixGrid = MatrixUtils.splitIn3x3(currentMatrix);

            // Throw exception when not divisible by 2 or 3
            } else {
                throw new RuntimeException("Not Implemented: The current grid is not divisible by 2 or by 3");
            }

            // Loop over all split matrices and check if they can be replaced by one of the transformation rules.
            for (Integer rowIndex : splitMatrixGrid.keySet()) {
                Map<Integer, Matrix> matrixRow = splitMatrixGrid.get(rowIndex);
                for (Integer colIndex : matrixRow.keySet()) {
                    Matrix transRuleReplaceMatrix = transformationRuleMap.get(matrixRow.get(colIndex).asString());
                    if (transRuleReplaceMatrix != null) {
                        matrixRow.put(colIndex, transRuleReplaceMatrix);
                    }
                }
            }

            currentMatrix = MatrixUtils.mergeMatrices(splitMatrixGrid);

            System.out.println("Iteration: " + (iteration + 1) + ", Pixels on: " + currentMatrix.getNumberOfPixelsOn());
        }
    }

    private static void storeTransformationInMap(Map<String, Matrix> transformationRuleMap, Matrix transRuleMatchMatrix, Matrix transRuleReplaceMatrix) {
        transformationRuleMap.put(transRuleMatchMatrix.asString(), transRuleReplaceMatrix);
        transformationRuleMap.put(MatrixUtils.mirrorHorizontal(transRuleMatchMatrix).asString(), transRuleReplaceMatrix);
        transformationRuleMap.put(MatrixUtils.mirrorVertical(transRuleMatchMatrix).asString(), transRuleReplaceMatrix);
    }
}
