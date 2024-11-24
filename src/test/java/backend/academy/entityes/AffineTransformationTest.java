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
        AffineTransformation transformation = AffineTransformation.getRandom(settingsLoader);
        assertNotNull(transformation);
        assertNotNull(transformation.getPixel());
    }

    @Test
    void applyAffineTransformation_shouldTransformPointCorrectly() {
        AffineTransformation transformation = new AffineTransformation(1, 0, 0, 1, 1, 1, new Pixel(0, 0, 0));
        Point point = new Point(1, 1);
        transformation.applyAffineTransformation(point);
        assertEquals(2, point.getX());
        assertEquals(2, point.getY());
    }

    @Test
    void applyAffineTransformation_shouldHandleZeroTransformation() {
        AffineTransformation transformation = new AffineTransformation(0, 0, 0, 0, 0, 0, new Pixel(0, 0, 0));
        Point point = new Point(1, 1);
        transformation.applyAffineTransformation(point);
        assertEquals(0, point.getX());
        assertEquals(0, point.getY());
    }

    @Test
    void applyAffineTransformation_shouldHandleNegativeValues() {
        AffineTransformation transformation = new AffineTransformation(-1, 0, 0, -1, -1, -1, new Pixel(0, 0, 0));
        Point point = new Point(1, 1);
        transformation.applyAffineTransformation(point);
        assertEquals(-2, point.getX());
        assertEquals(-2, point.getY());
    }
}
