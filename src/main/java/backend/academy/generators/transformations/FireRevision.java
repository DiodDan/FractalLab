package backend.academy.generators.transformations;

import backend.academy.entityes.Point;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.ThreadLocalRandom;

public class FireRevision implements Transformation {

    public static final double UPWARD_MOTION_CONSTANT = 0.6;
    public static final int TURBULENCE = 10;
    public static final double TURBULENCE_Y = 0.5;
    public static final double NOISE_STRENGTH = 0.1;
    public static final double COEF = 0.2;

    @Override
    @SuppressFBWarnings("PREDICTABLE_RANDOM")
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        double newX = Math.sin(theta) * Math.sin(r) * Math.sin(r);
        double newY = Math.cos(theta) * Math.sin(r) + UPWARD_MOTION_CONSTANT * y;
        newX += TURBULENCE_Y * Math.sin(TURBULENCE * y)
            + NOISE_STRENGTH * ThreadLocalRandom.current().nextInt(0, 2);
        newY += TURBULENCE_Y * Math.cos(TURBULENCE * x)
            + NOISE_STRENGTH * ThreadLocalRandom.current().nextInt(0, 2);
        double swirlStrength = Math.sin(r * NOISE_STRENGTH) * COEF;
        newX += swirlStrength * Math.cos(theta);
        newY += swirlStrength * Math.sin(theta);
        point.setX(newX);
        point.setY(newY);
    }

    @Override
    public String getFancyName() {
        return "Fire-Like Transformation";
    }
}
