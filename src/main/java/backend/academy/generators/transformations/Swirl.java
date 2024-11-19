package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Swirl implements Transformation {
    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.sqrt(x * x + y * y);
        point.setX(x * Math.sin(r * r) - y * Math.cos(r * r));
        point.setY(x * Math.cos(r * r) + y * Math.sin(r * r));
    }

    @Override
    public String getFancyName() {
        return "Swirl";
    }
}
