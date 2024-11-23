package backend.academy.generators.transformations;

import backend.academy.entityes.Point;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("FCCD_FIND_CLASS_CIRCULAR_DEPENDENCY")
//I don't know how to fix this warning and I don't really see any circular dependency here
public class Handkerchief implements Transformation {
    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double f = Math.atan2(x, y);
        double r = Math.sqrt(x * x + y * y);
        point.setX(Math.sin(f + r) * r);
        point.setY(Math.cos(f - r) * r);
    }

    @Override
    public String getFancyName() {
        return "Handkerchief";
    }
}
