package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class HorseshoeTransformation implements Transformation {

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        // Compute the distance (r) from the origin
        double r = Math.sqrt(x * x + y * y);

        // Apply the Horseshoe Transformation
        point.setX((1 / r) * ((x - y) * (x + y)));
        point.setY((1 / r) * (2 * x * y));
    }

    @Override
    public String getFancyName() {
        return "Horseshoe (Variation 4)";
    }
}
