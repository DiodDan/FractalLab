package backend.academy.entityes;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class FractalImage {
    private final int width;
    private final int height;
    private final Pixel[][] pixels;

    public FractalImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Pixel[width][height];
    }

    public Pixel getPixel(int x, int y) {
        return this.pixels[x][y];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        this.pixels[x][y] = pixel;
    }

    public void addPixel(int x, int y, Pixel pixel) {
        if (this.pixels[x][y] == null) {
            this.pixels[x][y] = pixel.copy();
        } else {
            this.pixels[x][y].add(pixel);
        }
    }

    public void render(BufferedImage image) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
                if (pixel != null) {
                    int rgb = new Color(pixel.r, pixel.g, pixel.b).getRGB();
                    image.setRGB(x, y, rgb);
                }
            }
        }
    }

    public void renderWithGamma(BufferedImage image, double gamma) {
        double max = 0;
        for (int row = 0; row < this.width; row++) {
            for (int col = 0; col < this.height; col++) {
                Pixel pixel = this.pixels[row][col];
                if (pixel != null && pixel.hitCount != 0) {
                    pixel.normalize();
                    if (pixel.normal > max) {
                        max = pixel.normal;
                    }
                }
            }
        }
        for (int row = 0; row < this.width; row++) {
            for (int col = 0; col < this.height; col++) {
                Pixel pixel = this.pixels[row][col];
                if (pixel != null) {
                    pixel.normal /= max;
                    int r = (int) (pixel.r * Math.pow(pixel.normal, (1.0 / gamma)));
                    int g = (int) (pixel.g * Math.pow(pixel.normal, (1.0 / gamma)));
                    int b = (int) (pixel.b * Math.pow(pixel.normal, (1.0 / gamma)));
                    image.setRGB(row, col, new Color(r, g, b).getRGB());
                }
            }
        }
    }
}
