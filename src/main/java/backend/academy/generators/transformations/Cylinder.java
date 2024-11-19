package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Cylinder implements Transformation {
    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        point.setX(Math.sin(x));
        point.setY(y);
    }

    @Override
    public String getFancyName() {
        return "Cylinder";
    }
}
