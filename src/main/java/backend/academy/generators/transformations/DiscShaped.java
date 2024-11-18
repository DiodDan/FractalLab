package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class DiscShaped implements Transformation {
    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double local = Math.PI * Math.sqrt(x * x + y * y);
        point.setX((1 / Math.PI) * Math.atan(y / x) * Math.sin(local));
        point.setY((1 / Math.PI) * Math.atan(y / x) * Math.cos(local));
    }

    @Override
    public String getFancyName() {
        return "Disc shaped";
    }
}
