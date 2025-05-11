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

    public static Map<RowColIndex, Matrix> splitIn2x2(Matrix matrix) {
        Map<RowColIndex, Matrix> matrixGrid2x2 = new HashMap<>();
        if (matrix.getSize() == 2) {
            matrixGrid2x2.put(new RowColIndex(0, 0), matrix);
            return matrixGrid2x2;
        }
        for (int rowIndex = 0; rowIndex < matrix.getSize();) {
            PixelRow[] firstRowPixels = RowUtils.splitBy2(matrix.getPixelRow(rowIndex));
            PixelRow[] secondRowPixels = RowUtils.splitBy2(matrix.getPixelRow(rowIndex + 1));
            for (int matrixIndex = 0; matrixIndex < firstRowPixels.length; matrixIndex++) {
                matrixGrid2x2.put(
                        new RowColIndex(rowIndex, matrixIndex),
                        new Matrix(firstRowPixels[matrixIndex], secondRowPixels[matrixIndex]));
            }
            rowIndex += 2;
        }
        return matrixGrid2x2;
    }

    public static Map<RowColIndex, Matrix> splitIn3x3(Matrix matrix) {
        Map<RowColIndex, Matrix> matrixGrid3x3 = new HashMap<>();
        if (matrix.getSize() == 3) {
            matrixGrid3x3.put(new RowColIndex(0, 0), matrix);
            return matrixGrid3x3;
        }
        for (int rowIndex = 0; rowIndex < matrix.getSize();) {
            PixelRow[] firstRowPixels = RowUtils.splitBy3(matrix.getPixelRow(rowIndex));
            PixelRow[] secondRowPixels = RowUtils.splitBy3(matrix.getPixelRow(rowIndex + 1));
            PixelRow[] thirdRowPixels = RowUtils.splitBy3(matrix.getPixelRow(rowIndex + 2));
            for (int matrixIndex = 0; matrixIndex < firstRowPixels.length; matrixIndex++) {
                matrixGrid3x3.put(
                        new RowColIndex(rowIndex, matrixIndex),
                        new Matrix(firstRowPixels[matrixIndex], secondRowPixels[matrixIndex], thirdRowPixels[matrixIndex])
                );
            }
            rowIndex += 3;
        }
        return matrixGrid3x3;
    }

    public static Matrix mergeMatrices(Map<RowColIndex, Matrix> matrixGrid) {
        return null;
    }
}
