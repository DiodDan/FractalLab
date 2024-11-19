package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Fire implements Transformation {

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
    double newY = Math.cos(theta) * Math.sin(r) + 0.5 * y; // Upward motion

    // Add turbulence for a more fire-like shape
    newX += 0.3 * Math.sin(5 * y);
    newY += 0.3 * Math.cos(5 * x);

    // Rotation angle in radians
    double alpha = Math.toRadians(50);

    // Apply rotation
    double rotatedX = newX * Math.cos(alpha) - newY * Math.sin(alpha);
    double rotatedY = newX * Math.sin(alpha) + newY * Math.cos(alpha);

    // Set transformed coordinates
    point.setX(rotatedX);
    point.setY(rotatedY);
}

    @Override
    public String getFancyName() {
        return "Custom Fire";
    }
}
