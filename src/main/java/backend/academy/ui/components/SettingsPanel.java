package backend.academy.ui.components;

import backend.academy.SettingsLoader;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SettingsPanel extends JPanel {
    private final Property imageWidthProperty;
    private final Property imageHeightProperty;
    private final Property generatorXMINProperty;
    private final Property generatorXMAXProperty;
    private final Property generatorYMINProperty;
    private final Property generatorYMAXProperty;
    private final Property generatorNonDrawMovesProperty;
    private final Property affineTransformationsAmountProperty;
    private final Property drawersAmountProperty;
    private final Property iterationsProperty;
    private final Property xBiasMinProperty;
    private final Property xBiasMaxProperty;
    private final Property yBiasMinProperty;
    private final Property yBiasMaxProperty;
    private final Property gammaProperty;
    private final Property maxThreadsProperty;

    public SettingsPanel(
        SettingsLoader settingsLoader,
        JButton runtimeSettingsButton,
        JButton regenerateImageButton
    ) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Settings that can be applied within one generation
        JPanel generationSettingsPanel = new JPanel(new GridLayout(0, 1));
        generationSettingsPanel.setBorder(BorderFactory.createTitledBorder("Runtime Applicable Settings"));

        generatorXMINProperty = addProperty(
            generationSettingsPanel,
            "Generator XMIN",
            String.valueOf(settingsLoader.getGeneratorXMIN()),
            "Minimum X value for the generator()"
        );
        generatorXMAXProperty = addProperty(
            generationSettingsPanel,
            "Generator XMAX",
            String.valueOf(settingsLoader.getGeneratorXMAX()),
            "Maximum X value for the generator"
        );
        generatorYMINProperty = addProperty(
            generationSettingsPanel,
            "Generator YMIN",
            String.valueOf(settingsLoader.getGeneratorYMIN()),
            "Minimum Y value for the generator"
        );
        generatorYMAXProperty = addProperty(
            generationSettingsPanel,
            "Generator YMAX",
            String.valueOf(settingsLoader.getGeneratorYMAX()),
            "Maximum Y value for the generator"
        );
        generatorNonDrawMovesProperty = addProperty(
            generationSettingsPanel,
            "Generator Non-Draw Moves",
            String.valueOf(settingsLoader.getGeneratorNonDrawMoves()),
            "Number of non-draw moves for the generator"
        );
        affineTransformationsAmountProperty = addProperty(
            generationSettingsPanel,
            "Affine Transformations Amount",
            String.valueOf(settingsLoader.getAffineTransformationsAmount()),
            "Amount of affine transformations"
        );
        drawersAmountProperty = addProperty(
            generationSettingsPanel,
            "Drawers Amount",
            String.valueOf(settingsLoader.getDrawersAmount()),
            "Amount of drawers"
        );
        iterationsProperty = addProperty(
            generationSettingsPanel,
            "Iterations",
            String.valueOf(settingsLoader.getIterations()),
            "Number of iterations"
        );
        xBiasMinProperty = addProperty(
            generationSettingsPanel,
            "X Bias Min",
            String.valueOf(settingsLoader.getXBiasMin()),
            "Minimum X bias"
        );
        xBiasMaxProperty = addProperty(
            generationSettingsPanel,
            "X Bias Max",
            String.valueOf(settingsLoader.getXBiasMax()),
            "Maximum X bias"
        );
        yBiasMinProperty = addProperty(
            generationSettingsPanel,
            "Y Bias Min",
            String.valueOf(settingsLoader.getYBiasMin()),
            "Minimum Y bias"
        );
        yBiasMaxProperty = addProperty(
            generationSettingsPanel,
            "Y Bias Max",
            String.valueOf(settingsLoader.getYBiasMax()),
            "Maximum Y bias"
        );
        gammaProperty = addProperty(
            generationSettingsPanel,
            "Gamma",
            String.valueOf(settingsLoader.getGamma()),
            "Gamma value"
        );
        maxThreadsProperty = addProperty(
            generationSettingsPanel,
            "Max Threads",
            String.valueOf(settingsLoader.getMaxDrawerThreads()),
            "Maximum number of threads"
        );

        // Settings that affect the application
        JPanel applicationSettingsPanel = new JPanel(new GridLayout(0, 1));
        applicationSettingsPanel.setBorder(BorderFactory.createTitledBorder("Regenerate Image Settings"));
        imageWidthProperty = addProperty(
            applicationSettingsPanel,
            "Image Width",
            String.valueOf(settingsLoader.getImageWidth()),
            "Width of the image"
        );
        imageHeightProperty = addProperty(
            applicationSettingsPanel,
            "Image Height",
            String.valueOf(settingsLoader.getImageHeight()),
            "Height of the image"
        );

        generationSettingsPanel.setBackground(UIManager.getColor("Panel.background"));
        applicationSettingsPanel.setBackground(UIManager.getColor("Panel.background"));

        this.add(generationSettingsPanel);
        this.add(runtimeSettingsButton);
        this.add(applicationSettingsPanel);
        this.add(regenerateImageButton);
    }

    private Property addProperty(JPanel panel, String label, String value, String tooltip) {
        Property property = new Property(label, tooltip);
        property.setText(value);
        panel.add(property);
        return property;
    }

    public void updateSettingsLoader(SettingsLoader settingsLoader) {
        settingsLoader.setImageWidth(Integer.parseInt(imageWidthProperty.getText()));
        settingsLoader.setImageHeight(Integer.parseInt(imageHeightProperty.getText()));
        settingsLoader.setGeneratorXMIN(Double.parseDouble(generatorXMINProperty.getText()));
        settingsLoader.setGeneratorXMAX(Double.parseDouble(generatorXMAXProperty.getText()));
        settingsLoader.setGeneratorYMIN(Double.parseDouble(generatorYMINProperty.getText()));
        settingsLoader.setGeneratorYMAX(Double.parseDouble(generatorYMAXProperty.getText()));
        settingsLoader.setGeneratorNonDrawMoves(Integer.parseInt(generatorNonDrawMovesProperty.getText()));
        settingsLoader.setAffineTransformationsAmount(
            Integer.parseInt(affineTransformationsAmountProperty.getText()));
        settingsLoader.setDrawersAmount(Integer.parseInt(drawersAmountProperty.getText()));
        settingsLoader.setIterations(Integer.parseInt(iterationsProperty.getText()));
        settingsLoader.setXBiasMin(Double.parseDouble(xBiasMinProperty.getText()));
        settingsLoader.setXBiasMax(Double.parseDouble(xBiasMaxProperty.getText()));
        settingsLoader.setYBiasMin(Double.parseDouble(yBiasMinProperty.getText()));
        settingsLoader.setYBiasMax(Double.parseDouble(yBiasMaxProperty.getText()));
        settingsLoader.setGamma(Double.parseDouble(gammaProperty.getText()));
        settingsLoader.setMaxDrawerThreads(Integer.parseInt(maxThreadsProperty.getText()));
    }
}
