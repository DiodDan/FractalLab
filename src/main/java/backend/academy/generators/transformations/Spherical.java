package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Spherical implements Transformation {

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        point.setX(x / (x * x + y * y));
        point.setY(y / (x * x + y * y));
    }

    @Override
    public String getFancyName() {
        return "Spherical";
    }
}
