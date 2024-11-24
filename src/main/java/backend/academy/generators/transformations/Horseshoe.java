package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class Horseshoe extends Transformation {

    public Horseshoe(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        // Compute the distance (r) from the origin
        double r = Math.sqrt(x * x + y * y);

        // Apply the Horseshoe Transformation

        double newX = (1 / r) * ((x - y) * (x + y));
        double newY = (1 / r) * (2 * x * y);

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Horseshoe (Variation 4)";
    }
}
