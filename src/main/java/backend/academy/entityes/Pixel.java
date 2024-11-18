package backend.academy.entityes;

public class Pixel {
    int r;
    int g;
    int b;
    int hitCount;
    double normal;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hitCount = 1;
        this.normal = 0;
    }

    public Pixel copy() {
        return new Pixel(this.r, this.g, this.b);
    }

    public void add(Pixel pixel) {
        this.r = (this.r + pixel.r) / 2;
        this.g = (this.g + pixel.g) / 2;
        this.b = (this.b + pixel.b) / 2;
        this.hitCount += pixel.hitCount;
    }

    public void normalize() {
        this.normal = Math.log10(this.hitCount);
    }
}
