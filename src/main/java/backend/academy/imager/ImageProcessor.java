package backend.academy.imager;

import backend.academy.SettingsLoader;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageProcessor {

    public static final int ALPHA_SHIFT = 24;
    public static final int BASE = 0xFF;
    public static final int RED_SHIFT = 16;
    public static final int GREEN_SHIFT = 8;
    public static final int TWO_TO_POWER_OF_7 = 128;
    public static final int MAX_COLOR_NUMBER = 255;
    private final SettingsLoader settings;

    public ImageProcessor(SettingsLoader settings) {
        this.settings = settings;
    }

    public void adjustContrast(BufferedImage image) {
        double contrastFactor = settings.getImageContrast();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);

                int alpha = (rgb >> ALPHA_SHIFT) & BASE;
                int red = clamp(applyContrast((rgb >> RED_SHIFT) & BASE, contrastFactor));
                int green = clamp(applyContrast((rgb >> GREEN_SHIFT) & BASE, contrastFactor));
                int blue = clamp(applyContrast(rgb & BASE, contrastFactor));

                image.setRGB(x, y, (alpha << ALPHA_SHIFT)
                    | (red << RED_SHIFT)
                    | (green << GREEN_SHIFT)
                    | blue);
            }
        }
    }

    public BufferedImage resizeImage(BufferedImage originalImage) {
        int originalWidth = settings.getImageWidth();
        int originalHeight = settings.getImageHeight();
        int targetWidth = settings.getSaveWidth();
        int targetHeight = settings.getSaveHeight();

        if (originalWidth == targetWidth && originalHeight == targetHeight) {
            // Return the original image if resizing isn't needed
            return originalImage;
        }

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        return resizedImage;
    }

    private int applyContrast(int colorValue, double contrastFactor) {
        return (int) ((colorValue - TWO_TO_POWER_OF_7) * contrastFactor + TWO_TO_POWER_OF_7);
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(MAX_COLOR_NUMBER, value));
    }
}
