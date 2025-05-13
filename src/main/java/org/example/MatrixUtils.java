package org.example;

import java.util.HashMap;
import java.util.Map;

public class MatrixUtils {

    public static PixelRow[] mapColumnsToRows(PixelColumn[] pixelColumns) {
        int size = pixelColumns.length;
        PixelRow[] pixelRows = new PixelRow[size];
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            char[] pixelRow = new char[size];
            for (int colIndex = 0; colIndex < size; colIndex++) {
                pixelRow[colIndex] = pixelColumns[colIndex].getPixel(rowIndex);
            }
            pixelRows[rowIndex] = new PixelRow(pixelRow);
        }
        return pixelRows;
    }

    public static Matrix mirrorHorizontal(Matrix matrix) {
        int size = matrix.getSize();
        PixelRow[] mirroredPixelRows = new PixelRow[size];
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            mirroredPixelRows[rowIndex] = matrix.getPixelRow(size - 1 - rowIndex);
        }
        return new Matrix(mirroredPixelRows);
    }

    public static Matrix mirrorVertical(Matrix matrix) {
        int size = matrix.getSize();
        PixelColumn[] mirroredPixelColumns = new PixelColumn[size];
        for (int colIndex = 0; colIndex < size; colIndex++) {
            mirroredPixelColumns[colIndex] = matrix.getPixelColumn(size - 1 - colIndex);
        }
        return new Matrix(mapColumnsToRows(mirroredPixelColumns));
    }

    public static Matrix rotate90ClockWise(Matrix matrix) {
        int size = matrix.getSize();
        PixelColumn[] rotatedPixelColumns = new PixelColumn[size];
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            rotatedPixelColumns[size - 1 - rowIndex] = new PixelColumn(matrix.getPixelRow(rowIndex).getAllPixels());
        }
        return new Matrix(mapColumnsToRows(rotatedPixelColumns));
    }

    public static Map<Integer, Map<Integer, Matrix>> splitIn2x2(Matrix matrix) {
        Map<Integer, Map<Integer, Matrix>> matrixGrid2x2 = new HashMap<>();
        if (matrix.getSize() == 2) {
            Map<Integer, Matrix> matrixRow2x2 = new HashMap<>();
            matrixRow2x2.put(0, matrix);
            matrixGrid2x2.put(0, matrixRow2x2);
            return matrixGrid2x2;
        }
        for (int rowIndex = 0; rowIndex < matrix.getSize();) {
            Map<Integer, Matrix> matrixRow2x2 = new HashMap<>();
            PixelRow[] firstRowPixels = RowUtils.splitInRowsWithSize(matrix.getPixelRow(rowIndex), 2);
            PixelRow[] secondRowPixels = RowUtils.splitInRowsWithSize(matrix.getPixelRow(rowIndex + 1), 2);
            for (int matrixIndex = 0; matrixIndex < firstRowPixels.length; matrixIndex++) {
                matrixRow2x2.put(matrixIndex, new Matrix(firstRowPixels[matrixIndex], secondRowPixels[matrixIndex]));
            }
            matrixGrid2x2.put(rowIndex / 2, matrixRow2x2);
            rowIndex += 2;
        }
        return matrixGrid2x2;
    }

    public static Map<Integer, Map<Integer, Matrix>> splitIn3x3(Matrix matrix) {
        Map<Integer, Map<Integer, Matrix>> matrixGrid3x3 = new HashMap<>();
        if (matrix.getSize() == 3) {
            Map<Integer, Matrix> matrixRow3x3 = new HashMap<>();
            matrixRow3x3.put(0, matrix);
            matrixGrid3x3.put(0, matrixRow3x3);
            return matrixGrid3x3;
        }
        for (int rowIndex = 0; rowIndex < matrix.getSize();) {
            Map<Integer, Matrix> matrixRow3x3 = new HashMap<>();
            PixelRow[] firstRowPixels = RowUtils.splitInRowsWithSize(matrix.getPixelRow(rowIndex), 3);
            PixelRow[] secondRowPixels = RowUtils.splitInRowsWithSize(matrix.getPixelRow(rowIndex + 1), 3);
            PixelRow[] thirdRowPixels = RowUtils.splitInRowsWithSize(matrix.getPixelRow(rowIndex + 2), 3);
            for (int matrixIndex = 0; matrixIndex < firstRowPixels.length; matrixIndex++) {
                matrixRow3x3.put(matrixIndex, new Matrix(firstRowPixels[matrixIndex], secondRowPixels[matrixIndex], thirdRowPixels[matrixIndex]));
            }
            matrixGrid3x3.put(rowIndex / 3, matrixRow3x3);
            rowIndex += 3;
        }
        return matrixGrid3x3;
    }

    public static Matrix mergeMatrices(Map<Integer, Map<Integer, Matrix>> matrixGrid) {
        int internalMatrixSize = matrixGrid.get(0).get(0).getSize();
        Matrix resultMatrix = new Matrix(matrixGrid.size() * internalMatrixSize);

        for (Integer rowIndex : matrixGrid.keySet()) {
            for (Integer colIndex : matrixGrid.keySet()) {
                Matrix matrix = matrixGrid.get(rowIndex).get(colIndex);
                int rowOffset = rowIndex * internalMatrixSize;
                int colOffset = colIndex * internalMatrixSize;
                resultMatrix.setMatrix(rowOffset, colOffset, matrix);
            }
        }

        return resultMatrix;
    }
}
