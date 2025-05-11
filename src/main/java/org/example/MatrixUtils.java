package org.example;

import java.util.LinkedList;
import java.util.List;

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

    public static List<Matrix> splitInFour(Matrix matrix) {
        int size = matrix.getSize();
        List<Matrix> splitMatrix = new LinkedList<>();
        PixelRow[] leftMatrixRows = new PixelRow[size / 2];
        PixelRow[] rightMatrixRows = new PixelRow[size / 2];
        for (int rowindex = 0; rowindex < (size / 2); rowindex++) {
            leftMatrixRows[rowindex] = RowUtils.getFirstHalf(matrix.getPixelRow(rowindex));
            rightMatrixRows[rowindex] = RowUtils.getSecondHalf(matrix.getPixelRow(rowindex));
        }
        splitMatrix.add(new Matrix(leftMatrixRows));
        splitMatrix.add(new Matrix(rightMatrixRows));
        for (int rowindex = (size / 2); rowindex < size; rowindex++) {
            leftMatrixRows[rowindex % 2] = RowUtils.getFirstHalf(matrix.getPixelRow(rowindex));
            rightMatrixRows[rowindex % 2] = RowUtils.getSecondHalf(matrix.getPixelRow(rowindex));
        }
        splitMatrix.add(new Matrix(leftMatrixRows));
        splitMatrix.add(new Matrix(rightMatrixRows));
        return splitMatrix;
    }
}
