package backend.academy.generators;

import backend.academy.SettingsLoader;
import backend.academy.entityes.AffineTransformation;
import backend.academy.entityes.FractalImage;
import backend.academy.entityes.Point;
import backend.academy.generators.transformations.Transformation;
import backend.academy.randomizer.Randomizer;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = false)
public class ImageGenerator {
    private final SettingsLoader settingsLoader = new SettingsLoader();
    private final Randomizer randomizer = new Randomizer();

    private double XMIN = settingsLoader.getGeneratorXMIN();
    private double XMAX = settingsLoader.getGeneratorXMAX();
    private double YMIN = settingsLoader.getGeneratorYMIN();
    private double YMAX = settingsLoader.getGeneratorYMAX();
    private int nonDrawMoves = settingsLoader.getGeneratorNonDrawMoves();
    @Getter private FractalImage fractalImage;

    public void startCalculation(
        SettingsLoader settingsLoader
    ) {
        int drawersAmount = settingsLoader.getDrawersAmount();
        List<AffineTransformation> affineTransformations = settingsLoader.getAffineTransformations();
        List<Transformation> functionalTransformations = settingsLoader.getFunctionalTransformations();
        int iterations = settingsLoader.getIterations();
        int width = settingsLoader.getImageWidth();
        int height = settingsLoader.getImageHeight();

        fractalImage = new FractalImage(width, height);
        for (int drawer = 0; drawer < drawersAmount; drawer++) {
            System.out.println("Drawer " + drawer + " started");
            Point newPoint = new Point(randomizer.randomDouble(XMIN, XMAX), randomizer.randomDouble(YMIN, YMAX));
            for (int step = -nonDrawMoves; step < iterations; step++) {
                AffineTransformation affineTransformation = randomizer.randomElement(affineTransformations);
                Transformation functionalTransformation = randomizer.randomElement(functionalTransformations);

                newPoint.applyAffineTransformation(affineTransformation);
                functionalTransformation.apply(newPoint);

                if (step >= 0
                    && newPoint.xFits(XMIN, XMAX)
                    && newPoint.yFits(YMIN, YMAX)
                ) {
                    int x = calculateX(newPoint, width);
                    int y = calculateY(newPoint, height);
                    if (x >= 0 && x < width && y >= 0 && y < height) {
                        fractalImage.addPixel(x, y, affineTransformation.getPixel());
                    }
                }
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
            }
        }
    }

    private int calculateX(Point point, int width) {
        return width - (int) Math.floor(((XMAX - point.getX()) / (XMAX - XMIN)) * width);
    }

    private int calculateY(Point point, int height) {
        return height - (int) Math.floor(((YMAX - point.getY()) / (YMAX - YMIN)) * height);
    }

}
