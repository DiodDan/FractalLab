package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class Waves extends Transformation {

    public Waves(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    private static final double B = 1.0; // parameter for y-axis wave effect
    private static final double C = 2.0; // parameter for frequency control (y)
    private static final double E = 0.5; // parameter for x-axis wave effect
    private static final double F = 1.5; // parameter for frequency control (x)

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        double newX = x + B * Math.sin(y / (C * C));
        double newY = y + E * Math.sin(x / (F * F));

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Waves (Variation 15)";
    }
}
