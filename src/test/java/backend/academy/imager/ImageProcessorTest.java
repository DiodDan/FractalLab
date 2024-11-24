package backend.academy.imager;

import static org.junit.jupiter.api.Assertions.*;
import backend.academy.SettingsLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.awt.image.BufferedImage;

class ImageProcessorTest {

    private SettingsLoader settings;
    private ImageProcessor imageProcessor;

    @BeforeEach
    void setUp() {
        settings = Mockito.mock(SettingsLoader.class);
        imageProcessor = new ImageProcessor(settings);
    }

    @Test
    void adjustContrast_shouldHandleMinAndMaxValues() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFF000000); // Black
        image.setRGB(1, 0, 0xFFFFFFFF); // White

        Mockito.when(settings.getImageContrast()).thenReturn(2.0F);

        imageProcessor.adjustContrast(image);

        assertEquals(0xFF000000, image.getRGB(0, 0));
        assertEquals(0xFFFFFFFF, image.getRGB(1, 0));
    }

    @Test
    void resizeImage_shouldResizeCorrectly() {
        BufferedImage originalImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        Mockito.when(settings.getImageWidth()).thenReturn(100);
        Mockito.when(settings.getImageHeight()).thenReturn(100);
        Mockito.when(settings.getSaveWidth()).thenReturn(50);
        Mockito.when(settings.getSaveHeight()).thenReturn(50);

        BufferedImage resizedImage = imageProcessor.resizeImage(originalImage);

        assertEquals(50, resizedImage.getWidth());
        assertEquals(50, resizedImage.getHeight());
    }

    @Test
    void resizeImage_shouldReturnOriginalIfNoResizeNeeded() {
        BufferedImage originalImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        Mockito.when(settings.getImageWidth()).thenReturn(100);
        Mockito.when(settings.getImageHeight()).thenReturn(100);
        Mockito.when(settings.getSaveWidth()).thenReturn(100);
        Mockito.when(settings.getSaveHeight()).thenReturn(100);

        BufferedImage resizedImage = imageProcessor.resizeImage(originalImage);

        assertSame(originalImage, resizedImage);
    }
}
