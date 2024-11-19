package backend.academy.imager;

import backend.academy.SettingsLoader;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageProcessor {

    SettingsLoader settings;

    public ImageProcessor(SettingsLoader settings) {
        this.settings = settings;
    }

    public void adjustContrast(BufferedImage originalImage) {
        double contrastFactor = settings.getImageContrast();

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int rgb = originalImage.getRGB(x, y);

                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Apply contrast adjustment
                red = clamp((int) ((red - 128) * contrastFactor + 128));
                green = clamp((int) ((green - 128) * contrastFactor + 128));
                blue = clamp((int) ((blue - 128) * contrastFactor + 128));

                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                originalImage.setRGB(x, y, newRgb);
            }
        }
    }

    public BufferedImage resizeImage(BufferedImage originalImage) {
        int targetWidth = settings.getSaveWidth();
        int targetHeight = settings.getSaveHeight();

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();

        if (settings.getImageWidth() != settings.getSaveWidth()
            || settings.getImageHeight() != settings.getSaveHeight()
        ) {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        } else {
            g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        }

        g.dispose();
        return resizedImage;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
