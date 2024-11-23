package backend.academy.generators.transformations;

import backend.academy.entityes.Point;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.ThreadLocalRandom;

public class Julia implements Transformation {
    @Override
    @SuppressFBWarnings("PREDICTABLE_RANDOM")
    public void apply(Point point) {
        double[] values = {0, Math.PI};
        double o = values[ThreadLocalRandom.current().nextInt(0, 2)];
        double x = point.getX();
        double y = point.getY();
        double f = Math.atan2(x, y);
        double r = Math.sqrt(x * x + y * y);
        point.setX(Math.sqrt(r) * Math.cos(f / 2 + o));
        point.setY(Math.sqrt(r) * Math.sin(f / 2 + o));
    }

    @Override
    public String getFancyName() {
        return "Julia";
    }
}
