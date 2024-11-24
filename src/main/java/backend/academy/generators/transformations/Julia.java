package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.ThreadLocalRandom;

public class Julia extends Transformation {

    public Julia(SettingsLoader settingsLoader) {
        super(settingsLoader);
    }

    @Override
    @SuppressFBWarnings("PREDICTABLE_RANDOM")
    public void apply(Point point) {
        double[] values = {0, Math.PI};
        double o = values[ThreadLocalRandom.current().nextInt(0, 2)];
        double x = point.getX();
        double y = point.getY();
        double f = Math.atan2(x, y);
        double r = Math.sqrt(x * x + y * y);

        double newX = Math.sqrt(r) * Math.cos(f / 2 + o);
        double newY = Math.sqrt(r) * Math.sin(f / 2 + o);

        point.setX(applyXMirroring(newX, newY));
        point.setY(applyYMirroring(newX, newY));
    }

    @Override
    public String getFancyName() {
        return "Julia";
    }
}
