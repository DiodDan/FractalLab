package backend.academy.entityes;

import java.awt.Color;

public class Pixel {
    public int r;
    public int g;
    public int b;
    public int hitCount;
    public double normal;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hitCount = 1;
        this.normal = 0;
    }

    public Pixel(Color color) {
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.hitCount = 1;
        this.normal = 0;
    }

    public Pixel copy() {
        return new Pixel(this.r, this.g, this.b);
    }

    public void add(Pixel pixel) {
        this.r = (this.r * this.hitCount + pixel.r) / (this.hitCount + 1);
        this.g = (this.g * this.hitCount + pixel.g) / (this.hitCount + 1);
        this.b = (this.b * this.hitCount + pixel.b) / (this.hitCount + 1);
        this.hitCount += pixel.hitCount;
    }

    public void normalize() {
        this.normal = Math.log10(this.hitCount);
    }

    public Color getColor() {
        return new Color(this.r, this.g, this.b);
    }
}
