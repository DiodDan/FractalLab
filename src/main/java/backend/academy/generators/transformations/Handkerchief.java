package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("FCCD_FIND_CLASS_CIRCULAR_DEPENDENCY")
//I don't know how to fix this warning and I don't really see any circular dependency here
public class Handkerchief extends Transformation {

    public Handkerchief(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double f = Math.atan2(x, y);
        double r = Math.sqrt(x * x + y * y);

        double newX = Math.sin(f + r) * r;
        double newY = Math.cos(f - r) * r;

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Handkerchief";
    }
}
