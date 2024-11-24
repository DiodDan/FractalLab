package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class Diamond extends Transformation {

    public Diamond(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double f = Math.atan2(y, x);  // Use Math.atan2 to handle all quadrants
        double r = Math.sqrt(x * x + y * y);

        double newX = Math.sin(f) * Math.cos(r);
        double newY = Math.cos(f) * Math.sin(r);

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Diamond";
    }
}
