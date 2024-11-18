package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class HeartShaped implements Transformation {

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double local = Math.sqrt(x * x + y * y);
        point.setX(local
            * Math.sin(local * Math.atan(y / x)));
        point.setY(-local
            * Math.cos(local * Math.atan(y / x)));
    }

    @Override
    public String getFancyName() {
        return "Heart Shaped";
    }
}
