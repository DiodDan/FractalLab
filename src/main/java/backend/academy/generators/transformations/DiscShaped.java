package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class DiscShaped implements Transformation {
    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = Math.PI * Math.sqrt(x * x + y * y);
        point.setX((1 / Math.PI) * Math.atan2(y,  x) * Math.sin(r));
        point.setY((1 / Math.PI) * Math.atan2(y,  x) * Math.cos(r));
    }

    @Override
    public String getFancyName() {
        return "Disc shaped";
    }
}
