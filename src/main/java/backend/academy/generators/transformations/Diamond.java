package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Diamond implements Transformation {
    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        double f = Math.atan2(y, x);  // Use Math.atan2 to handle all quadrants
        double r = Math.sqrt(x * x + y * y);

        point.setX(Math.sin(f) * Math.cos(r));
        point.setY(Math.cos(f) * Math.sin(r));

    }

    @Override
    public String getFancyName() {
        return "Diamond";
    }
}
