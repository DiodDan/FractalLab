package backend.academy.generators;

import backend.academy.SettingsLoader;
import backend.academy.entityes.AffineTransformation;
import backend.academy.entityes.FractalImage;
import backend.academy.entityes.Point;
import backend.academy.generators.transformations.Transformation;
import backend.academy.randomizer.Randomizer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = false)
public class ImageGenerator {
    private final SettingsLoader settingsLoader;
    @Getter private FractalImage fractalImage;
    @Getter int drawersFinished = 0;

    public ImageGenerator(SettingsLoader settingsLoader) {
        this.settingsLoader = settingsLoader;
    }

    public void startCalculation() {
        double XMIN = settingsLoader.getGeneratorXMIN();
        double XMAX = settingsLoader.getGeneratorXMAX();
        double YMIN = settingsLoader.getGeneratorYMIN();
        double YMAX = settingsLoader.getGeneratorYMAX();
        int nonDrawMoves = settingsLoader.getGeneratorNonDrawMoves();
        int drawersAmount = settingsLoader.getDrawersAmount();
        int maxDrawerThreads = settingsLoader.getMaxDrawerThreads();
        List<AffineTransformation> affineTransformations = settingsLoader.getAffineTransformations();
        List<Transformation> functionalTransformations = settingsLoader.getFunctionalTransformations();
        int iterations = settingsLoader.getIterations();
        int width = settingsLoader.getImageWidth();
        int height = settingsLoader.getImageHeight();
        drawersFinished = 0;

        fractalImage = new FractalImage(width, height);

        // Create a thread pool with a maximum number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(maxDrawerThreads);

        for (int drawer = 0; drawer < drawersAmount; drawer++) {
            int finalDrawer = drawer;
            executorService.submit(() -> {
                System.out.println("Drawer " + finalDrawer + " started");
                Point newPoint = new Point(ThreadLocalRandom.current().nextDouble(XMIN, XMAX), ThreadLocalRandom.current().nextDouble(YMIN, YMAX));
                for (int step = -nonDrawMoves; step < iterations; step++) {
                    AffineTransformation affineTransformation =
                        affineTransformations.get(ThreadLocalRandom.current().nextInt(affineTransformations.size()));
                    Transformation functionalTransformation = functionalTransformations.get(
                        ThreadLocalRandom.current().nextInt(functionalTransformations.size()));

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
                            fractalImage.incrementHits();

                        }
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                }
                drawersFinished++;
            });
        }

        // Shutdown the executor service and wait for all tasks to complete
        executorService.shutdown();
        try {
            executorService.close();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread pool interrupted: " + e.getMessage());
        }
    }

    private int calculateX(Point point, int width) {
        double XMIN = settingsLoader.getGeneratorXMIN();
        double XMAX = settingsLoader.getGeneratorXMAX();
        return width - (int) Math.floor(((XMAX - point.getX()) / (XMAX - XMIN)) * width);
    }

    private int calculateY(Point point, int height) {
        double YMIN = settingsLoader.getGeneratorYMIN();
        double YMAX = settingsLoader.getGeneratorYMAX();
        return height - (int) Math.floor(((YMAX - point.getY()) / (YMAX - YMIN)) * height);
    }
}
