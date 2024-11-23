package backend.academy.imager;

import backend.academy.SettingsLoader;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageSaver {
    private final SettingsLoader settings;

    public ImageSaver(SettingsLoader settingsLoader) {
        this.settings = settingsLoader;
    }

    public ImageSaveMessage saveImage(BufferedImage image) {
        try {
            File file = getSaveFile();
            ImageIO.write(image, "png", file);

            return new ImageSaveMessage(
                "<html>Image saved successfully!<br><a href='file:///"
                    + file.getParentFile().getAbsolutePath().replace('\\', '/')
                    + "'>Open Directory</a></html>",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException ex) {
            return new ImageSaveMessage("Error saving image: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressFBWarnings("PATH_TRAVERSAL_IN")
    // This value in file is not set by user I don't see any risk of path traversal
    private File getSaveFile() throws IOException {
        String imageFormat = ".png";
        String imageBaseName = sanitizeFileName(settings.getImageSaveFileBaseName());

        File imagesDir = new File(sanitizeFilePath(settings.getImageSaveDir()));
        if (!imagesDir.exists() && !imagesDir.mkdirs()) {
            throw new IOException("Failed to create directory: " + imagesDir.getAbsolutePath());
        }

        File file = new File(imagesDir, imageBaseName + imageFormat);
        int counter = 1;
        while (file.exists()) {
            file = new File(imagesDir, sanitizeFileName(imageBaseName + '_' + counter) + imageFormat);
            counter++;
        }
        return file;
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }

    private String sanitizeFilePath(String filePath) {
        return filePath.replaceAll("[^a-zA-Z0-9-_\\/\\.]", "_");
    }

    public record ImageSaveMessage(String message, String title, int messageType) {
    }
}
