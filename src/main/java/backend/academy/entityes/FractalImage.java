package backend.academy.entityes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import lombok.Getter;

public class FractalImage {
    private final int width;
    private final int height;
    @Getter private int hitsFromLastCheck = 0;
    private final Pixel[][] pixels;

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
        double maxNormal = findMaxNormal();

        if (maxNormal == 0) {
            return; // No pixels to render.
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
                if (pixel != null) {
                    pixel.normal /= maxNormal;
                    int r = applyGamma(pixel.r, pixel.normal, gamma);
                    int g = applyGamma(pixel.g, pixel.normal, gamma);
                    int b = applyGamma(pixel.b, pixel.normal, gamma);
                    image.setRGB(x, y, new Color(r, g, b).getRGB());
                }
            }
        }
    }

    private double findMaxNormal() {
        double max = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
                if (pixel != null && pixel.isHit()) {
                    pixel.normalize();
                    max = Math.max(max, pixel.normal);
                }
            }
        }
        return max;
    }

    private int applyGamma(double colorValue, double normal, double gamma) {
        return (int) (colorValue * Math.pow(normal, 1.0 / gamma));
    }
}
