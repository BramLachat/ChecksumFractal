package org.example;

public class Matrix {
    private char[][] pixels;
    private int size;

    private char[][] mapPixelRowsToCharMatrix(PixelRow[] pixelRows) {
        char[][] pixelMatrix = new char[this.size][this.size];
        for (int rowIndex = 0; rowIndex < pixelRows.length; rowIndex++) {
            pixelMatrix[rowIndex] = pixelRows[rowIndex].getAllPixels();
        }
        return pixelMatrix;
    }

    private PixelRow[] getCharMatrixAsPixelRows() {
        PixelRow[] pixelRows = new PixelRow[this.pixels.length];
        for (int rowIndex = 0; rowIndex < this.pixels.length; rowIndex++) {
            pixelRows[rowIndex] = new PixelRow(this.pixels[rowIndex]);
        }
        return pixelRows;
    }

    public Matrix(int size) {
        this.size = size;
        this.pixels = new char[size][size];
    }

    public Matrix(String pixelString) {
        String[] pixelRowStrings = pixelString.split("/");
        this.size = pixelRowStrings.length;
        PixelRow[] pixelRows = new PixelRow[this.size];
        for (int i = 0; i < this.size; i++) {
            pixelRows[i] = new PixelRow(pixelRowStrings[i]);
        }
        this.pixels = mapPixelRowsToCharMatrix(pixelRows);
    }

    public Matrix(PixelRow[] pixelRows) {
        this.size = pixelRows.length;
        this.pixels = mapPixelRowsToCharMatrix(pixelRows);
    }

    public Matrix(PixelRow firstRow, PixelRow secondRow) {
        PixelRow[] pixelRows = new PixelRow[2];
        pixelRows[0] = firstRow;
        pixelRows[1] = secondRow;
        this.size = pixelRows.length;
        this.pixels = mapPixelRowsToCharMatrix(pixelRows);
    }

    public Matrix(PixelRow firstRow, PixelRow secondRow, PixelRow thirdRow) {
        PixelRow[] pixelRows = new PixelRow[3];
        pixelRows[0] = firstRow;
        pixelRows[1] = secondRow;
        pixelRows[2] = thirdRow;
        this.size = pixelRows.length;
        this.pixels = mapPixelRowsToCharMatrix(pixelRows);
    }

    public int getSize() {
        return size;
    }

    public void print() {
        for (PixelRow pixelRow : getCharMatrixAsPixelRows()) {
            pixelRow.print();
            System.out.println();
        }
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        for (PixelRow pixelRow : getCharMatrixAsPixelRows()) {
            sb.append(pixelRow.asString());
        }
        return sb.toString();
    }

    public PixelRow getPixelRow(int index) {
        return getCharMatrixAsPixelRows()[index];
    }

    public PixelColumn getPixelColumn(int columnIndex) {
        char[] pixelColumn = new char[size];
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            pixelColumn[rowIndex] = getPixelRow(rowIndex).getPixel(columnIndex);
        }
        return new PixelColumn(pixelColumn);
    }

    public int getNumberOfPixelsOn() {
        int numberOfPixelsOn = 0;
        for (PixelRow pixelRow : getCharMatrixAsPixelRows()) {
            numberOfPixelsOn += pixelRow.getNumberOfPixelsOn();
        }
        return numberOfPixelsOn;
    }

    private char[][] getAllPixels() {
        return this.pixels;
    }

    public void setMatrix(int rowOffset, int colOffset, Matrix matrix) {
        char[][] matrixPixels = matrix.getAllPixels();
        for (int rowIndex = 0; rowIndex < matrixPixels.length; rowIndex++) {
            char[] rowPixels = matrixPixels[rowIndex];
            for (int colIndex = 0; colIndex < rowPixels.length; colIndex++) {
                char pixel = rowPixels[colIndex];
                this.pixels[rowOffset + rowIndex][colOffset + colIndex] = pixel;
            }
        }
    }
}
