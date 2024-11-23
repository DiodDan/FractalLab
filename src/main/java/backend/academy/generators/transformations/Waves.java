package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public class Waves implements Transformation {

    private final double b; // parameter for y-axis wave effect
    private final double c; // parameter for frequency control (y)
    private final double e; // parameter for x-axis wave effect
    private final double f; // parameter for frequency control (x)

    public Waves(double b, double c, double e, double f) {
        this.b = b;
        this.c = c;
        this.e = e;
        this.f = f;
    }

    @Override
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        point.setX(x + b * Math.sin(y / (c * c)));
        point.setY(y + e * Math.sin(x / (f * f)));
    }

    @Override
    public String getFancyName() {
        return "Waves (Variation 15)";
    }
}
