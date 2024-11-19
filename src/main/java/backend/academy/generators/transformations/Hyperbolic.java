package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Hyperbolic implements Transformation {
    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        double f = Math.atan2(x, y);
        double r = Math.sqrt(x * x + y * y);

        point.setX(Math.sin(f) / r);
        point.setY(r * Math.cos(f));
    }

    @Override
    public String getFancyName() {
        return "Hyperbolic";
    }
}
