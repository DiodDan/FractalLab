package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class Fire extends Transformation {

    public Fire(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }


    public static final double UPWARD_MOTION_CONSTANT = 0.5;
    public static final int ROTATION_ANGEL = 50;
    public static final int TURBULENCE = 5;
    public static final double TURBULENCE_Y = 0.3;

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        // Radial distance
        double r = Math.sqrt(x * x + y * y);

        // Angle (polar coordinate)
        double theta = Math.atan2(y, x);

        // Fire-like skew and compress
        double newX = Math.sin(theta) * Math.sin(r);
        double newY = Math.cos(theta) * Math.sin(r) + UPWARD_MOTION_CONSTANT * y; // Upward motion

        // Add turbulence for a more fire-like shape
        newX += TURBULENCE_Y * Math.sin(TURBULENCE * y);
        newY += TURBULENCE_Y * Math.cos(TURBULENCE * x);

        // Rotation angle in radians
        double alpha = Math.toRadians(ROTATION_ANGEL);

        // Apply rotation
        double rotatedX = newX * Math.cos(alpha) - newY * Math.sin(alpha);
        double rotatedY = newX * Math.sin(alpha) + newY * Math.cos(alpha);

        // Set transformed coordinates
        point.setX(applyXMirroring(rotatedX, rotatedY));
        point.setY(applyYMirroring(rotatedX, rotatedY));
    }

    @Override
    public String getFancyName() {
        return "Custom Fire";
    }
}
