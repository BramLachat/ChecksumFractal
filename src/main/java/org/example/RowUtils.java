package org.example;

public class RowUtils {

    public static PixelRow[] splitInRowsWithSize(PixelRow pixelRow, int newRowSize) {
        // Assumption: Check is performed before this function is called that row is divisible by {newRowSize}.
        int oldRowSize = pixelRow.getSize();
        int amountOfNewRows = oldRowSize / newRowSize;
        PixelRow[] splitPixelRows = new PixelRow[amountOfNewRows];
        for (int colIndex = 0; colIndex < oldRowSize;) {
            char[] pixels = new char[newRowSize];
            for (int newColIndex = 0; newColIndex < newRowSize; newColIndex++) {
                pixels[newColIndex] = pixelRow.getPixel(colIndex + newColIndex);
            }
            int newRowCounter = colIndex / newRowSize;
            splitPixelRows[newRowCounter] = new PixelRow(pixels);
            colIndex += newRowSize;
        }
        return splitPixelRows;
    }
}
