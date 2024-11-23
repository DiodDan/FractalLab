package backend.academy.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Setter
@Getter
@Accessors(fluent = false)
public class Point {
    double x;
    double y;

    public void applyAffineTransformation(AffineTransformation affineTransformation) {
        affineTransformation.applyAffineTransformation(this);
    }

    public boolean xFits(double xMin, double xMax) {
        return x >= xMin && x <= xMax;
    }

    public boolean yFits(double yMin, double yMax) {
        return y >= yMin && y <= yMax;
    }
}
