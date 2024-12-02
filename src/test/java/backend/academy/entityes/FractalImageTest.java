package backend.academy.entityes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FractalImageTest {

    private FractalImage fractalImage;
    private Pixel pixel;

    @BeforeEach
    void setUp() {
        fractalImage = new FractalImage(10, 10);
        pixel = new Pixel(100, 150, 200);
    }

    @Test
    void addPixel_shouldAddPixelCorrectly() {
        // Given
        // A pixel with predefined color values

        // When
        fractalImage.addPixel(1, 1, pixel);

        // Then
        assertEquals(pixel, fractalImage.getPixels()[1][1]);
    }

    @Test
    void addPixel_shouldAccumulatePixelValues() {
        // Given
        fractalImage.addPixel(1, 1, pixel);

        // When
        // The second pixel with values (50, 50, 50) is added
        fractalImage.addPixel(1, 1, new Pixel(50, 50, 50));

        // Then
        // The resulting pixel values should be averaged:
        // R: (100 * 1 + 50) / (1 + 1) = 75
        // G: (150 * 1 + 50) / (1 + 1) = 100
        // B: (200 * 1 + 50) / (1 + 1) = 125
        // for more info See Pixel.add method
        Pixel result = fractalImage.getPixels()[1][1];
        assertEquals(75, result.getR());
        assertEquals(100, result.getG());
        assertEquals(125, result.getB());
    }

    @Test
    void incrementHits_shouldIncreaseHitsFromLastCheck() {
        // Given
        // A FractalImage with an initial hit count of 0

        // When
        fractalImage.incrementHits();

        // Then
        assertEquals(1, fractalImage.resetHits());
    }

    @Test
    void resetHits_shouldResetHitsFromLastCheck() {
        // Given
        fractalImage.incrementHits();

        // When
        // Reset the hit count and then reset it again
        int hits = fractalImage.resetHits();

        // Then
        // The first reset should return 1, and the second reset should return 0
        assertEquals(1, hits);
        assertEquals(0, fractalImage.resetHits());
    }

    @Test
    void hashPixelsState_shouldReturnConsistentHash() {
        // Given
        fractalImage.addPixel(1, 1, pixel);

        // When
        int hash1 = fractalImage.hashPixelsState();
        int hash2 = fractalImage.hashPixelsState();

        // Then
        // Both hashes should be the same, as the state has not changed
        assertEquals(hash1, hash2);
    }

    @Test
    void hashPixelsState_shouldChangeWhenPixelsChange() {
        // Given
        fractalImage.addPixel(1, 1, pixel);
        int hash1 = fractalImage.hashPixelsState();

        // When
        // Add another pixel to position (2, 2)
        fractalImage.addPixel(2, 2, new Pixel(50, 50, 50));
        int hash2 = fractalImage.hashPixelsState();

        // Then
        // The hash should be different after modifying the pixel array
        assertNotEquals(hash1, hash2);
    }
}
