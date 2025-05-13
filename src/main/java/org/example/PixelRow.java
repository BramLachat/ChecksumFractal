package org.example;

public class PixelRow {
    private char[] pixels;

    public PixelRow(String pixelString) {
        this.pixels = pixelString.toCharArray();
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
