package backend.academy.imager;

import backend.academy.SettingsLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JOptionPane;

public class ImageSaver {
    private final SettingsLoader settings;

    public ImageSaver(SettingsLoader settingsLoader) {
        this.settings = settingsLoader;
    }

    public ImageSaveMessage saveImage(BufferedImage image) {
        try {
            File file = getSaveFile();
            javax.imageio.ImageIO.write(image, "png", file);

            return new ImageSaveMessage(
                "<html>Image saved successfully!<br><a href='file:///"
                    + file.getParentFile().getAbsolutePath().replace("\\", "/")
                    + "'>Open Directory</a></html>",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (java.io.IOException ex) {
            return new ImageSaveMessage("Error saving image: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private File getSaveFile() {
        String imageFormat = ".png";

        String imageBaseName = settings.getImageSaveFileBaseName();

        File imagesDir = new File(settings.getImageSaveDir());
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }

        File file = new File(imagesDir, imageBaseName + imageFormat);
        int counter = 1;
        while (file.exists()) {
            file = new File(imagesDir, imageBaseName + "_" + counter + imageFormat);
            counter++;
        }
        return file;
    }

    public record ImageSaveMessage(String message, String title, int messageType) {
    }
}
