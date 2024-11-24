package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class Hyperbolic extends Transformation {

    public Hyperbolic(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        double f = Math.atan2(x, y);
        double r = Math.sqrt(x * x + y * y);


        double newX = Math.sin(f) / r;
        double newY = r * Math.cos(f);

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Hyperbolic";
    }
}
