package org.example;

public class PixelColumn {
    private char[] pixels;

    public PixelColumn(char[] pixels) {
        this.pixels = pixels;
    }

    public char getPixel(int index) {
        return pixels[index];
    }
}
