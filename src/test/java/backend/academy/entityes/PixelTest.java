package backend.academy.entityes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PixelTest {

    @Test
    void pixelInitialization_shouldSetCorrectValues() {
        Pixel pixel = new Pixel(100, 150, 200);
        assertEquals(100, pixel.getR());
        assertEquals(150, pixel.getG());
        assertEquals(200, pixel.getB());
    }

    @Test
    void equals_shouldReturnFalseForDifferentPixels() {
        Pixel pixel1 = new Pixel(100, 150, 200);
        Pixel pixel2 = new Pixel(50, 50, 50);
        assertFalse(pixel1.equals(pixel2));
    }

    @Test
    void hashCode_shouldReturnDifferentHashForDifferentPixels() {
        Pixel pixel1 = new Pixel(100, 150, 200);
        Pixel pixel2 = new Pixel(50, 50, 50);
        assertNotEquals(pixel1.hashCode(), pixel2.hashCode());
    }
}
