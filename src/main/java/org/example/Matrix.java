package org.example;

public class Matrix {
    private PixelRow[] pixelRows;
    private int size;

    public static Matrix initialize(String pixelString) {
        String[] pixelRowStrings = pixelString.split("/");
        int size = pixelRowStrings.length;
        PixelRow[] pixelRows = new PixelRow[size];
        for (int i = 0; i < size; i++) {
            pixelRows[i] = PixelRow.initialize(pixelRowStrings[i]);
        }
        return new Matrix(pixelRows);
    }

    public Matrix(PixelRow[] pixelRows) {
        this.pixelRows = pixelRows;
        this.size = pixelRows.length;
    }

    public Matrix(PixelRow firstRow, PixelRow secondRow) {
        this.pixelRows = new PixelRow[2];
        this.pixelRows[0] = firstRow;
        this.pixelRows[1] = secondRow;
        this.size = pixelRows.length;
    }

    public Matrix(PixelRow firstRow, PixelRow secondRow, PixelRow thirdRow) {
        this.pixelRows = new PixelRow[3];
        this.pixelRows[0] = firstRow;
        this.pixelRows[1] = secondRow;
        this.pixelRows[2] = thirdRow;
        this.size = pixelRows.length;
    }

    public int getSize() {
        return size;
    }

    public void print() {
        for (PixelRow pixelRow : pixelRows) {
            pixelRow.print();
            System.out.println();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PixelRow pixelRow : pixelRows) {
            sb.append(pixelRow.toString());
        }
        return sb.toString();
    }

    public PixelRow getPixelRow(int index) {
        return pixelRows[index];
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
        for (PixelRow pixelRow : pixelRows) {
            numberOfPixelsOn += pixelRow.getNumberOfPixelsOn();
        }
        return numberOfPixelsOn;
    }

    public boolean matches(Matrix matrix) {
        if (size != matrix.size) {
            return false;
        }
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            if (!getPixelRow(rowIndex).matches(matrix.getPixelRow(rowIndex))) {
                return false;
            }
        }
        return true;
    }
}
