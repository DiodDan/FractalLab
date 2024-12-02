package backend.academy.entityes;

import java.awt.Color;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = false)
public class Pixel {
    private int r;
    private int g;
    private int b;
    private int hitCount;
    private double normal;

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

    public boolean isHit() {
        return this.hitCount > 0;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Pixel pixel)) {
            return false;
        }
        return pixel.r == r && pixel.g == g && pixel.b == b;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + r;
        result = 31 * result + g;
        result = 31 * result + b;
        return result;
    }
}
