package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Spherical implements Transformation {

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.sqrt(x * x + y * y);
        point.setX(x / (r * r));
        point.setY(y / (r * r));
    }

    @Override
    public String getFancyName() {
        return "Spherical";
    }
}
