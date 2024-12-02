package backend.academy.entityes;

import static org.junit.jupiter.api.Assertions.*;
import backend.academy.SettingsLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AffineTransformationTest {

    private SettingsLoader settingsLoader;

    @BeforeEach
    void setUp() {
        settingsLoader = new SettingsLoader();
        settingsLoader.setXBiasMin(-1);
        settingsLoader.setXBiasMax(1);
        settingsLoader.setYBiasMin(-1);
        settingsLoader.setYBiasMax(1);
    }

    @Test
    void getRandom_shouldReturnValidAffineTransformation() {
        // Given
        // A settingsLoader with predefined bias values

        // When
        AffineTransformation transformation = AffineTransformation.getRandom(settingsLoader);

        // Then
        assertNotNull(transformation);
        assertNotNull(transformation.getPixel());
        assertTrue(transformation.getPixel().getR() >= 0 && transformation.getPixel().getR() <= 255);
        assertTrue(transformation.getPixel().getG() >= 0 && transformation.getPixel().getG() <= 255);
        assertTrue(transformation.getPixel().getB() >= 0 && transformation.getPixel().getB() <= 255);
    }

    @Test
    void applyAffineTransformation_shouldTransformPointCorrectly() {
        // Given
        AffineTransformation transformation = new AffineTransformation(1, 0, 0, 1, 1, 1, new Pixel(0, 0, 0));
        Point point = new Point(1, 1);
        Point expectedPoint = new Point(2, 2);
        // We are expecting to have point(2, 2) as
        // x = a * x + b * y + e which is x = 1 * 1 + 0 * 1 + 1 = 2
        // y = c * x + d * y + f which is y = 0 * 1 + 1 * 1 + 1 = 2

        // When
        transformation.applyAffineTransformation(point);

        // Then
        assertEquals(expectedPoint, point);
    }

    @Test
    void applyAffineTransformation_shouldHandleZeroTransformation() {
        // Given
        AffineTransformation transformation = new AffineTransformation(0, 0, 0, 0, 0, 0, new Pixel(0, 0, 0));
        Point point = new Point(1, 1);
        Point expectedPoint = new Point(0, 0);
        // We are expecting to have point(0, 0) as
        // x = a * x + b * y + e which is x = 0 * 1 + 0 * 1 + 0 = 0
        // y = c * x + d * y + f which is y = 0 * 1 + 0 * 1 + 0 = 0

        // When
        transformation.applyAffineTransformation(point);

        // Then
        assertEquals(expectedPoint, point);
    }

    @Test
    void applyAffineTransformation_shouldHandleNegativeValues() {
        // Given
        AffineTransformation transformation = new AffineTransformation(-1, 0, 0, -1, -1, -1, new Pixel(0, 0, 0));
        Point point = new Point(1, 1);
        Point expectedPoint = new Point(-2, -2);
        // We are expecting to have point(-2, -2) as
        // x = a * x + b * y + e which is x = -1 * 1 + -1 * 0 - 1 = -2
        // y = c * x + d * y + f which is y = 0 * 1 + -1 * 1 - 1 = -2

        // When
        transformation.applyAffineTransformation(point);

        // Then
        assertEquals(expectedPoint, point);
    }
}
