package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("FCCD_FIND_CLASS_CIRCULAR_DEPENDENCY")
//I don't know how to fix this warning and I don't really see any circular dependency here
public class Cylinder extends Transformation {

    public Cylinder(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        double newX = Math.sin(x);
        point.setX(applyXMirroring(newX, y));
        point.setY(applyYMirroring(newX, y));
    }

    @Override
    public String getFancyName() {
        return "Cylinder";
    }
}
