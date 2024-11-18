package backend.academy.generators.transformations;

import backend.academy.entityes.Point;

public interface Transformation {
    void apply(Point point);

    String getFancyName();
}
