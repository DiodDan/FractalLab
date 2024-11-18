package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Polar implements Transformation {

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        point.setX(Math.atan(y / x) / Math.PI);
        point.setY(Math.sqrt(x * x + y * y) - 1);
    }

    @Override
    public String getFancyName() {
        return "Polar";
    }
}
