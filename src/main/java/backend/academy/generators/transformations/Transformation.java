package backend.academy.generators.transformations;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Point;

public abstract class Transformation {
    protected final SettingsLoader settings;

    public Transformation(SettingsLoader settingsLoader) {
        this.settings = settingsLoader;
    }

    public abstract void apply(Point point);

    public abstract String getFancyName();

    /**
     * Applies mirroring along the Y-axis if enabled in the settings.
     * Symmetry relative to the center vertically.
     */
    protected double applyXMirroring(double x, double y) {
        if (settings.isMirrorX()) {
            return Math.abs(x);
        }
        return x;
    }

    /**
     * Applies mirroring along the X-axis if enabled in the settings.
     * Symmetry relative to the center horizontally.
     */
    protected double applyYMirroring(double x, double y) {
        if (settings.isMirrorY()) {
            return Math.abs(y);
        }
        return y;
    }
}
