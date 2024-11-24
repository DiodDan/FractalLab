package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class Swirl extends Transformation {

    public Swirl(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.sqrt(x * x + y * y);

        double newX = x * Math.sin(r * r) - y * Math.cos(r * r);
        double newY = x * Math.cos(r * r) + y * Math.sin(r * r);

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Swirl";
    }
}
