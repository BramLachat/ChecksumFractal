package org.example;

public class RowUtils {
    public static PixelRow[] splitBy2(PixelRow pixelRow) {
        int size = pixelRow.getSize();
        PixelRow[] result = new PixelRow[size / 2];
        for (int colIndex = 0; colIndex < size;) {
            char[] pixels = new char[2];
            pixels[0] = pixelRow.getPixel(colIndex);
            pixels[1] = pixelRow.getPixel(colIndex + 1);
            result[colIndex / 2] = new PixelRow(pixels);
            colIndex += 2;
        }
        return result;
    }

    public static PixelRow[] splitBy3(PixelRow pixelRow) {
        int size = pixelRow.getSize();
        PixelRow[] result = new PixelRow[size / 3];
        for (int colIndex = 0; colIndex < size;) {
            char[] pixels = new char[3];
            pixels[0] = pixelRow.getPixel(colIndex);
            pixels[1] = pixelRow.getPixel(colIndex + 1);
            pixels[2] = pixelRow.getPixel(colIndex + 2);
            result[colIndex / 3] = new PixelRow(pixels);
            colIndex += 3;
        }
        return result;
    }
}
