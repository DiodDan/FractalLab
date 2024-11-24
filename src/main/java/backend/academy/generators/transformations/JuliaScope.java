package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.ThreadLocalRandom;

public class JuliaScope extends Transformation {

    public JuliaScope(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    public static final double POWER = 20.0;
    public static final double DISTANCE = 0.2;

    @Override
    @SuppressFBWarnings("PREDICTABLE_RANDOM")
    public void apply(Point point) {
        double x = point.getX();
        double y = point.getY();

        // Calculate polar coordinates
        double r = Math.sqrt(x * x + y * y); // Radius
        double theta = Math.atan2(y, x);    // Angle (phi)

        // Determine p3 and t
        // p1 = juliaScope.power
        double p3 = Math.floor(ThreadLocalRandom.current().nextDouble() * POWER); // Random p3 in the range [0, power)
        double t = (theta + 2 * Math.PI * p3) / POWER;

        // Calculate scaling factor
        // p2 = juliaScope.dist
        double scale = Math.pow(r, DISTANCE / POWER);

        // Update the point
        double newX = scale * Math.cos(t);
        double newY = scale * Math.sin(t);
        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "JuliaScope";
    }
}
