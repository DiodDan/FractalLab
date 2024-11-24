package backend.academy.entityes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = false)
public class FractalImage {
    private final int width;
    private final int height;
    private int hitsFromLastCheck = 0;
    @Getter private final Pixel[][] pixels;
    public static final int HASHING_COEFFICIENT = 31;
    public static final int HASH_STARTING_VALUE = 7;

    public FractalImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Pixel[width][height];
    }

    public void addPixel(int x, int y, Pixel pixel) {
        if (pixels[x][y] == null) {
            pixels[x][y] = pixel.copy();
        } else {
            pixels[x][y].add(pixel);
        }
    }

    public void incrementHits() {
        hitsFromLastCheck++;
    }

    public int resetHits() {
        int hits = hitsFromLastCheck;
        hitsFromLastCheck = 0;
        return hits;
    }

    public void renderWithGamma(BufferedImage image, double gamma) {
        double maxNormal = normalizePixels();

        if (maxNormal == 0) {
            return; // No pixels to render.
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
                if (pixel != null) {
                    pixel.setNormal(pixel.getNormal() / maxNormal);
                    int r = applyGamma(pixel.getR(), pixel.getNormal(), gamma);
                    int g = applyGamma(pixel.getG(), pixel.getNormal(), gamma);
                    int b = applyGamma(pixel.getB(), pixel.getNormal(), gamma);
                    image.setRGB(x, y, new Color(r, g, b).getRGB());
                }
            }
        }
    }

    public int hashPixelsState() {
        int hash = HASH_STARTING_VALUE;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
                if (pixel != null) {
                    hash = HASHING_COEFFICIENT * hash + pixel.hashCode();
                }
            }
        }
        return hash;
    }

    private double normalizePixels() {
        double max = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
                if (pixel != null && pixel.isHit()) {
                    pixel.normalize();
                    max = Math.max(max, pixel.getNormal());
                }
            }
        }
        return max;
    }

    private int applyGamma(double colorValue, double normal, double gamma) {
        return (int) (colorValue * Math.pow(normal, 1.0 / gamma));
    }
}
