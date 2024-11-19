package backend.academy.randomizer;

import backend.academy.SettingsLoader;
import backend.academy.entityes.Pixel;
import java.security.SecureRandom;
import java.util.List;

public class Randomizer {
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
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            );
        }
    }

    public Pixel randomColorConnectedToMain(SettingsLoader settingsLoader) {
        Pixel mainColor = new Pixel(settingsLoader.getThemColors()[random.nextInt(3)]);
        int variation = settingsLoader.getColorVariation();

        int red = Math.min(255, Math.max(0, mainColor.r + randomInt(-variation, variation + 1)));
        int green = Math.min(255, Math.max(0, mainColor.g + randomInt(-variation, variation + 1)));
        int blue = Math.min(255, Math.max(0, mainColor.b + randomInt(-variation, variation + 1)));
        return new Pixel(red, green, blue);
    }
}

