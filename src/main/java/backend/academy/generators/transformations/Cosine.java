package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class Cosine extends Transformation {

    public Cosine(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        double newX = Math.cos(Math.PI * x) * Math.cosh(y);
        double newY = -Math.sin(Math.PI * x) * Math.sinh(y);

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Cosine (Variation 20)";
    }
}
