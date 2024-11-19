package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class JuliaScope implements Transformation {

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        // Calculate polar coordinates
        double r = Math.sqrt(x * x + y * y); // Radius
        double theta = Math.atan2(y, x);    // Angle (phi)

        // Determine p3 and t
        // p1 = juliaScope.power
        double power = 6.0;
        double p3 = Math.floor(Math.random() * power); // Random p3 in the range [0, power)
        double t = (theta + 2 * Math.PI * p3) / power;

        // Calculate scaling factor
        // p2 = juliaScope.dist
        double distance = 0.8;
        double scale = Math.pow(r, distance / power);

        // Update the point
        double newX = scale * Math.cos(t);
        double newY = scale * Math.sin(t);
        point.setX(newX);
        point.setY(newY);
    }

    @Override
    public String getFancyName() {
        return "JuliaScope";
    }
}
