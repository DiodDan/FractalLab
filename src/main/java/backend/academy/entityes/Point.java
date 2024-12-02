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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point point)) {
            return false;
        }
        return point.x == x && point.y == y;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Double.hashCode(x);
        result = 31 * result + Double.hashCode(y);
        return result;
    }
}
