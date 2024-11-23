package backend.academy.randomizer;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Pixel;
import java.security.SecureRandom;

public class Randomizer {
    public static final int BOUND = 256;
    public static final int THEME_COLORS = 3;
    SecureRandom random = new SecureRandom();

    public double randomDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public Pixel randomPixel(SettingsLoader settingsLoader) {
        if (settingsLoader.isUseThemedColorGeneration()) {
            return randomColorConnectedToMain(settingsLoader);
        } else {
            return new Pixel(
                random.nextInt(BOUND),
                random.nextInt(BOUND),
                random.nextInt(BOUND)
            );
        }
    }

    public Pixel randomColorConnectedToMain(SettingsLoader settingsLoader) {
        Pixel mainColor = new Pixel(settingsLoader.getThemColors()[random.nextInt(THEME_COLORS)]);
        int variation = settingsLoader.getColorVariation();

        int red = Math.min(BOUND - 1, Math.max(0, mainColor.getR() + randomInt(-variation, variation + 1)));
        int green = Math.min(BOUND - 1, Math.max(0, mainColor.getG() + randomInt(-variation, variation + 1)));
        int blue = Math.min(BOUND - 1, Math.max(0, mainColor.getB() + randomInt(-variation, variation + 1)));
        return new Pixel(red, green, blue);
    }
}

