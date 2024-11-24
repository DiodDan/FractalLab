package backend.academy.entityes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        fractalImage.addPixel(1, 1, pixel);
        assertTrue(pixel.getColor().equals(fractalImage.getPixels()[1][1].getColor()));
    }

    @Test
    void addPixel_shouldAccumulatePixelValues() {
        fractalImage.addPixel(1, 1, pixel);
        fractalImage.addPixel(1, 1, new Pixel(50, 50, 50));
        Pixel result = fractalImage.getPixels()[1][1];
        assertEquals(75, result.getR());
        assertEquals(100, result.getG());
        assertEquals(125, result.getB());
    }

    @Test
    void incrementHits_shouldIncreaseHitsFromLastCheck() {
        fractalImage.incrementHits();
        assertEquals(1, fractalImage.resetHits());
    }

    @Test
    void resetHits_shouldResetHitsFromLastCheck() {
        fractalImage.incrementHits();
        int hits = fractalImage.resetHits();
        assertEquals(1, hits);
        assertEquals(0, fractalImage.resetHits());
    }

    @Test
    void hashPixelsState_shouldReturnConsistentHash() {
        fractalImage.addPixel(1, 1, pixel);
        int hash1 = fractalImage.hashPixelsState();
        int hash2 = fractalImage.hashPixelsState();
        assertEquals(hash1, hash2);
    }

    @Test
    void hashPixelsState_shouldChangeWhenPixelsChange() {
        fractalImage.addPixel(1, 1, pixel);
        int hash1 = fractalImage.hashPixelsState();
        fractalImage.addPixel(2, 2, new Pixel(50, 50, 50));
        int hash2 = fractalImage.hashPixelsState();
        assertNotEquals(hash1, hash2);
    }
}
