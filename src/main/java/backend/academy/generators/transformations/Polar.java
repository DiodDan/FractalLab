package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class Polar extends Transformation {

    public Polar(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        double newX = Math.atan2(y, x) / Math.PI;
        double newY = Math.sqrt(x * x + y * y) - 1;

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Polar";
    }
}
