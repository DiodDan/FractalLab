package backend.academy;

import backend.academy.entityes.AffineTransformation;
import backend.academy.generators.transformations.DiscShaped;
import backend.academy.generators.transformations.Spherical;
import backend.academy.generators.transformations.Transformation;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = false)
public class SettingsLoader {
    private final Properties properties = new Properties();
    // application settings
    private int appWidth;
    private int appHeight;

    // image settings
    @Setter private int imageWidth;
    @Setter private int imageHeight;

    // generator settings
    @Setter private double generatorXMIN;
    @Setter private double generatorXMAX;
    @Setter private double generatorYMIN;
    @Setter private double generatorYMAX;

    @Setter private int generatorNonDrawMoves;
    private final List<Transformation> availableFunctionalTransformations = List.of(
        new DiscShaped(),
        new Spherical()
    );
    private final List<AffineTransformation> affineTransformations = new ArrayList<>();
    @Setter private List<Transformation> functionalTransformations = List.of();
    @Setter private int affineTransformationsAmount;
    @Setter private int drawersAmount;
    @Setter private int iterations;
    @Setter private double xBiasMin;
    @Setter private double xBiasMax;
    @Setter private double yBiasMin;
    @Setter private double yBiasMax;
    @Setter private double gamma;

    public SettingsLoader() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);
            appWidth = Integer.parseInt(properties.getProperty("app.width"));
            appHeight = Integer.parseInt(properties.getProperty("app.height"));

            imageWidth = Integer.parseInt(properties.getProperty("image.width"));
            imageHeight = Integer.parseInt(properties.getProperty("image.height"));

            generatorXMIN = Double.parseDouble(properties.getProperty("generator.XMIN"));
            generatorXMAX = Double.parseDouble(properties.getProperty("generator.XMAX"));
            generatorYMIN = Double.parseDouble(properties.getProperty("generator.YMIN"));
            generatorYMAX = Double.parseDouble(properties.getProperty("generator.YMAX"));
            generatorNonDrawMoves = Integer.parseInt(properties.getProperty("generator.nonDrawMoves"));

            affineTransformationsAmount = Integer.parseInt(properties.getProperty("generator.affineTransformationsAmount"));
            drawersAmount = Integer.parseInt(properties.getProperty("generator.drawersAmount"));
            iterations = Integer.parseInt(properties.getProperty("generator.iterations"));

            xBiasMin = Double.parseDouble(properties.getProperty("generator.xBiasMin"));
            xBiasMax = Double.parseDouble(properties.getProperty("generator.xBiasMax"));
            yBiasMin = Double.parseDouble(properties.getProperty("generator.yBiasMin"));
            yBiasMax = Double.parseDouble(properties.getProperty("generator.yBiasMax"));

            gamma = Double.parseDouble(properties.getProperty("generator.gamma"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void generateAffineTransformations() {
        this.affineTransformations.clear();
        for (int i = 0; i < this.affineTransformationsAmount; i++) {
            this.affineTransformations.add(AffineTransformation.getRandom(this));
        }
    }
}
