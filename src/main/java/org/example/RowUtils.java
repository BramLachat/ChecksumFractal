package org.example;

public class RowUtils {
    public static PixelRow getFirstHalf(PixelRow pixelRow) {
        int size = pixelRow.getSize();
        char[] pixels = new char[size / 2];
        for (int colIndex = 0; colIndex < (size / 2); colIndex++) {
            pixels[colIndex] = pixelRow.getPixel(colIndex);
        }
        return new PixelRow(pixels);
    }

    public static PixelRow getSecondHalf(PixelRow pixelRow) {
        int size = pixelRow.getSize();
        char[] pixels = new char[size / 2];
        for (int colIndex = size / 2; colIndex < size; colIndex++) {
            pixels[colIndex % 2] = pixelRow.getPixel(colIndex);
        }
        return new PixelRow(pixels);
    }
}
