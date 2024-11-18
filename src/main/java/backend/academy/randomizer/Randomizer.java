package backend.academy.randomizer;

import java.security.SecureRandom;
import java.util.List;

public class Randomizer {
    SecureRandom random = new SecureRandom();

    public double randomDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public <T> T randomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
