package backend.academy.entityes;

import backend.academy.SettingsLoader;
import backend.academy.randomizer.Randomizer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(fluent = false)
public class AffineTransformation {
    public double a;
    public double b;
    public double c;
    public double d;
    public double e;
    public double f;
    private @Getter Pixel pixel;

    public static AffineTransformation getRandom(SettingsLoader settingsLoader) {
        Randomizer randomizer = new Randomizer();
        double[] coefficients = generateRandomCoefficients(randomizer);
        double e = randomizer.randomDouble(settingsLoader.getXBiasMin(), settingsLoader.getXBiasMax());
        double f = randomizer.randomDouble(settingsLoader.getYBiasMin(), settingsLoader.getYBiasMax());

        return new AffineTransformation(
            coefficients[0], coefficients[1], coefficients[2], coefficients[3], e, f,
            randomizer.randomPixel(settingsLoader)
        );
    }

    private static double[] generateRandomCoefficients(Randomizer randomizer) {
        double a, b, c, d;
        do {
            a = randomizer.randomDouble(-1, 1);
            b = randomizer.randomDouble(-1, 1);
            c = randomizer.randomDouble(-1, 1);
            d = randomizer.randomDouble(-1, 1);
        } while (isCompressionConditionMet(a, b, c, d));
        return new double[] {a, b, c, d};
    }

    private static boolean isCompressionConditionMet(double a, double b, double c, double d) {
        return Math.pow(a, 2) + Math.pow(c, 2) < 1.0
            && Math.pow(b, 2) + Math.pow(d, 2) < 1.0
            && Math.pow(b, 2) + Math.pow(d, 2) + Math.pow(a, 2) + Math.pow(c, 2) < 1.0 + Math.pow(a * d - b * c, 2);
    }

}
