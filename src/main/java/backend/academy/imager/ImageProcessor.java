package backend.academy.imager;

import backend.academy.SettingsLoader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcessor {

    private final SettingsLoader settings;

    public ImageProcessor(SettingsLoader settings) {
        this.settings = settings;
    }

    public void adjustContrast(BufferedImage image) {
        double contrastFactor = settings.getImageContrast();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);

                int alpha = (rgb >> 24) & 0xFF;
                int red = clamp(applyContrast((rgb >> 16) & 0xFF, contrastFactor));
                int green = clamp(applyContrast((rgb >> 8) & 0xFF, contrastFactor));
                int blue = clamp(applyContrast(rgb & 0xFF, contrastFactor));

                image.setRGB(x, y, (alpha << 24) | (red << 16) | (green << 8) | blue);
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
        return (int) ((colorValue - 128) * contrastFactor + 128);
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
