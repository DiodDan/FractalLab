package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public class DiscShaped extends Transformation {

    public DiscShaped(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.PI * Math.sqrt(x * x + y * y);

        double newX = (1 / Math.PI) * Math.atan2(y, x) * Math.sin(r);
        double newY = (1 / Math.PI) * Math.atan2(y, x) * Math.cos(r);

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Disc shaped";
    }
}
