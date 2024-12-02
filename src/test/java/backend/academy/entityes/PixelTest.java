package backend.academy.entityes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PixelTest {

    @Test
    void pixelInitialization_shouldSetCorrectValues() {
        // Given: A pixel with specific RGB values
        Pixel pixel = new Pixel(100, 150, 200);
        Pixel expectedPixel = new Pixel(100, 150, 200);

        // Then: The RGB values should match the initialized values
        assertEquals(pixel, expectedPixel);
    }

    @Test
    void equals_shouldReturnFalseForDifferentPixels() {
        // Given: Two pixels with different RGB values
        Pixel pixel1 = new Pixel(100, 150, 200);
        Pixel pixel2 = new Pixel(50, 50, 50);

        // Then: equals() should return false
        assertNotEquals(pixel1, pixel2);
    }

    @Test
    void hashCode_shouldReturnDifferentHashForDifferentPixels() {
        // Given: Two pixels with different RGB values
        Pixel pixel1 = new Pixel(100, 150, 200);
        Pixel pixel2 = new Pixel(50, 50, 50);

        // Then: Their hash codes should not match
        assertNotEquals(pixel1.hashCode(), pixel2.hashCode());
    }
}
