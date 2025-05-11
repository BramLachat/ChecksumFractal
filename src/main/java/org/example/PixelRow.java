package org.example;

public class PixelRow {
    private char[] pixels;

    public static PixelRow initialize(String pixelRow) {
        return new PixelRow(pixelRow.toCharArray());
    }

    public PixelRow(char[] pixels) {
        this.pixels = pixels;
    }

    public char getPixel(int index) {
        return pixels[index];
    }

    public int getSize() {
        return pixels.length;
    }

    public char[] getAllPixels() {
        return pixels;
    }

    public String toString() {
        return new String(pixels);
    }

    public boolean matches(PixelRow pixelRow) {
        if (pixelRow.pixels.length != pixels.length) {
            return false;
        }
        for (int index = 0; index < pixels.length; index++) {
            if (pixelRow.pixels[index] != pixels[index]) {
                return false;
            }
        }
        return true;
    }

    public void print() {
        for (char pixel : pixels) {
            System.out.print(pixel);
        }
    }

    public int getNumberOfPixelsOn() {
        int numberOfPixelsOn = 0;
        for (char pixel : pixels) {
            numberOfPixelsOn += pixel == '#' ? 1 : 0;
        }
        return numberOfPixelsOn;
    }
}
