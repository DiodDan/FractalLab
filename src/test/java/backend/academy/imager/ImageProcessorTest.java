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
        // Given: A mocked SettingsLoader and an ImageProcessor instance
        settings = Mockito.mock(SettingsLoader.class);
        imageProcessor = new ImageProcessor(settings);
    }

    @Test
    void adjustContrast_shouldHandleMinAndMaxValues() {
        // Given: An image with extreme pixel values (black and white)
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0xFF000000);
        image.setRGB(1, 0, 0xFFFFFFFF);
        Mockito.when(settings.getImageContrast()).thenReturn(2.0F);

        // When: Contrast adjustment is applied
        imageProcessor.adjustContrast(image);

        // Then: The pixel values should remain unchanged
        assertEquals(0xFF000000, image.getRGB(0, 0));
        assertEquals(0xFFFFFFFF, image.getRGB(1, 0));
    }

    @Test
    void resizeImage_shouldResizeCorrectly() {
        // Given: An image and resize settings
        BufferedImage originalImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Mockito.when(settings.getImageWidth()).thenReturn(100);
        Mockito.when(settings.getImageHeight()).thenReturn(100);
        Mockito.when(settings.getSaveWidth()).thenReturn(50);
        Mockito.when(settings.getSaveHeight()).thenReturn(50);

        // When: Resizing the image
        BufferedImage resizedImage = imageProcessor.resizeImage(originalImage);

        // Then: The resized image should have the correct dimensions
        assertEquals(50, resizedImage.getWidth());
        assertEquals(50, resizedImage.getHeight());
    }

    @Test
    void resizeImage_shouldReturnOriginalIfNoResizeNeeded() {
        // Given: An image and resize settings that match the original dimensions
        BufferedImage originalImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Mockito.when(settings.getImageWidth()).thenReturn(100);
        Mockito.when(settings.getImageHeight()).thenReturn(100);
        Mockito.when(settings.getSaveWidth()).thenReturn(100);
        Mockito.when(settings.getSaveHeight()).thenReturn(100);

        // When: Attempting to resize
        BufferedImage resizedImage = imageProcessor.resizeImage(originalImage);

        // Then: The original image should be returned unchanged
        assertSame(originalImage, resizedImage);
    }
}
