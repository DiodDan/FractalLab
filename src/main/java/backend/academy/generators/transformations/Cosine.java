package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Cosine implements Transformation {

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        point.setX(Math.cos(Math.PI * x) * Math.cosh(y));
        point.setY(-Math.sin(Math.PI * x) * Math.sinh(y));
    }

    @Override
    public String getFancyName() {
        return "Cosine (Variation 20)";
    }
}
