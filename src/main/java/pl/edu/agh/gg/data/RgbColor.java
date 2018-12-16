package pl.edu.agh.gg.data;

import java.awt.*;
import java.util.Objects;

public class RgbColor {

    private int r;
    private int g;
    private int b;

    public RgbColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public RgbColor(int rgb) {
        Color color = new Color(rgb);
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return String.format("R = %d, G = %d, B = %d", r, g, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RgbColor rgbColor = (RgbColor) o;
        return r == rgbColor.r &&
                g == rgbColor.g &&
                b == rgbColor.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }
}
