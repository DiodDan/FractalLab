package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class HeartShaped extends Transformation {

    public HeartShaped(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.sqrt(x * x + y * y);
        double f = Math.atan2(x, y);

        double newX = r * Math.sin(r * f);
        double newY = -r * Math.cos(r * f);

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Heart Shaped";
    }
}
