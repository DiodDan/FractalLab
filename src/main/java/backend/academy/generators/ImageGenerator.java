package backend.academy.generators;

import backend.academy.SettingsLoader;
import backend.academy.entityes.AffineTransformation;
import backend.academy.entityes.FractalImage;
import backend.academy.entityes.Point;
import backend.academy.generators.transformations.Transformation;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

@Accessors(fluent = false)
@Log4j2
public class ImageGenerator {
    private final SettingsLoader settingsLoader;
    @Getter private FractalImage fractalImage;
    @Getter private int drawersFinished;

    public ImageGenerator(SettingsLoader settingsLoader) {
        this.settingsLoader = settingsLoader;
        this.fractalImage = new FractalImage(settingsLoader.getImageWidth(), settingsLoader.getImageHeight());
    }

    public void startCalculation() {
        int drawersAmount = settingsLoader.getDrawersAmount();
        int maxDrawerThreads = settingsLoader.getMaxDrawerThreads();

        this.fractalImage = new FractalImage(settingsLoader.getImageWidth(), settingsLoader.getImageHeight());
        this.drawersFinished = 0;

        ExecutorService executorService = Executors.newFixedThreadPool(maxDrawerThreads);

        for (int drawer = 0; drawer < drawersAmount; drawer++) {
            final int drawerId = drawer;
            executorService.submit(() -> runDrawer(drawerId, settingsLoader));
        }

        shutdownExecutor(executorService);
    }

    private void runDrawer(int drawerId, SettingsLoader settings) {
        log.info("Drawer {} started", drawerId);

        Point currentPoint = generateRandomPoint(settings);

        for (int step = -settings.getGeneratorNonDrawMoves(); step < settings.getIterations(); step++) {
            if (Thread.currentThread().isInterrupted()) {
                log.warn("Drawer {} interrupted", drawerId);
                return;
            }

            // Apply transformations
            AffineTransformation affineTransformation = randomChoice(settings.getAffineTransformations());
            Transformation functionalTransformation = randomChoice(settings.getFunctionalTransformations());
            currentPoint.applyAffineTransformation(affineTransformation);
            functionalTransformation.apply(currentPoint);

            // Add pixel if within bounds
            if (step >= 0 && isWithinBounds(currentPoint, settings)) {
                recalculateAndAddPoint(settings, currentPoint, affineTransformation);
            }
            if (settings.isMirrorX() && step >= 0 && isWithinBounds(currentPoint, settings)) {
                currentPoint.setX(-currentPoint.getX());
                recalculateAndAddPoint(settings, currentPoint, affineTransformation);
            }
            if (settings.isMirrorY() && step >= 0 && isWithinBounds(currentPoint, settings)) {
                currentPoint.setY(-currentPoint.getY());
                recalculateAndAddPoint(settings, currentPoint, affineTransformation);
            }
            if (settings.isMirrorY() && settings.isMirrorX() && step >= 0 && isWithinBounds(currentPoint, settings)) {
                currentPoint.setX(-currentPoint.getX());
                recalculateAndAddPoint(settings, currentPoint, affineTransformation);
            }
        }

        incrementFinishedDrawers();
    }

    private void recalculateAndAddPoint(
        SettingsLoader settings,
        Point currentPoint,
        AffineTransformation affineTransformation
    ) {
        int x = calculateX(currentPoint, settings);
        int y = calculateY(currentPoint, settings);
        if (isValidPixel(x, y, settings)) {
            fractalImage.addPixel(x, y, affineTransformation.getPixel());
            fractalImage.incrementHits();
        }
    }

    private void shutdownExecutor(ExecutorService executorService) {
        executorService.close();
        try {
            if (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS)) {
                log.error("Executor service did not terminate properly");
            } else {
                log.info("Executor service terminated successfully");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Executor service interrupted: {}", e.getMessage());
        }
    }

    @SuppressFBWarnings("PREDICTABLE_RANDOM") // here I am using ThreadLocalRandom since it is way faster
    private Point generateRandomPoint(SettingsLoader settings) {
        return new Point(
            ThreadLocalRandom.current().nextDouble(settings.getGeneratorXMIN(), settings.getGeneratorXMAX()),
            ThreadLocalRandom.current().nextDouble(settings.getGeneratorYMIN(), settings.getGeneratorYMAX())
        );
    }

    @SuppressFBWarnings("PREDICTABLE_RANDOM") // here I am using ThreadLocalRandom since it is way faster
    private <T> T randomChoice(java.util.List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    private int calculateX(Point point, SettingsLoader settings) {
        double xMin = settings.getGeneratorXMIN();
        double xMax = settings.getGeneratorXMAX();
        return settings.getImageWidth()
            - (int) Math.floor(((xMax - point.getX()) / (xMax - xMin)) * settings.getImageWidth());
    }

    private int calculateY(Point point, SettingsLoader settings) {
        double yMin = settings.getGeneratorYMIN();
        double yMax = settings.getGeneratorYMAX();
        return settings.getImageHeight()
            - (int) Math.floor(((yMax - point.getY()) / (yMax - yMin)) * settings.getImageHeight());
    }

    private boolean isWithinBounds(Point point, SettingsLoader settings) {
        double xMin = settings.getGeneratorXMIN();
        double xMax = settings.getGeneratorXMAX();
        double yMin = settings.getGeneratorYMIN();
        double yMax = settings.getGeneratorYMAX();
        return point.getX() >= xMin && point.getX() <= xMax && point.getY() >= yMin && point.getY() <= yMax;
    }

    private boolean isValidPixel(int x, int y, SettingsLoader settings) {
        return x >= 0 && x < settings.getImageWidth() && y >= 0 && y < settings.getImageHeight();
    }

    private synchronized void incrementFinishedDrawers() {
        drawersFinished++;
    }
}
