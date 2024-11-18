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

    public SettingsPanel(
        SettingsLoader settingsLoader,
        JButton runtimeSettingsButton,
        JButton regenerateImageButton
        ) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Settings that can be applied within one generation
        JPanel generationSettingsPanel = new JPanel(new GridLayout(0, 1));
        generationSettingsPanel.setBorder(BorderFactory.createTitledBorder("Runtime Applicable Settings"));

        generatorXMINProperty =
            addProperty(generationSettingsPanel, "Generator XMIN", String.valueOf(settingsLoader.getGeneratorXMIN()));
        generatorXMAXProperty =
            addProperty(generationSettingsPanel, "Generator XMAX", String.valueOf(settingsLoader.getGeneratorXMAX()));
        generatorYMINProperty =
            addProperty(generationSettingsPanel, "Generator YMIN", String.valueOf(settingsLoader.getGeneratorYMIN()));
        generatorYMAXProperty =
            addProperty(generationSettingsPanel, "Generator YMAX", String.valueOf(settingsLoader.getGeneratorYMAX()));
        generatorNonDrawMovesProperty = addProperty(generationSettingsPanel, "Generator Non-Draw Moves",
            String.valueOf(settingsLoader.getGeneratorNonDrawMoves()));
        affineTransformationsAmountProperty = addProperty(generationSettingsPanel, "Affine Transformations Amount",
            String.valueOf(settingsLoader.getAffineTransformationsAmount()));
        drawersAmountProperty =
            addProperty(generationSettingsPanel, "Drawers Amount", String.valueOf(settingsLoader.getDrawersAmount()));
        iterationsProperty =
            addProperty(generationSettingsPanel, "Iterations", String.valueOf(settingsLoader.getIterations()));
        xBiasMinProperty =
            addProperty(generationSettingsPanel, "X Bias Min", String.valueOf(settingsLoader.getXBiasMin()));
        xBiasMaxProperty =
            addProperty(generationSettingsPanel, "X Bias Max", String.valueOf(settingsLoader.getXBiasMax()));
        yBiasMinProperty =
            addProperty(generationSettingsPanel, "Y Bias Min", String.valueOf(settingsLoader.getYBiasMin()));
        yBiasMaxProperty =
            addProperty(generationSettingsPanel, "Y Bias Max", String.valueOf(settingsLoader.getYBiasMax()));
        gammaProperty = addProperty(generationSettingsPanel, "Gamma", String.valueOf(settingsLoader.getGamma()));

        // Settings that affect the application
        JPanel applicationSettingsPanel = new JPanel(new GridLayout(0, 1));
        applicationSettingsPanel.setBorder(BorderFactory.createTitledBorder("Regenerate Image Settings"));
        imageWidthProperty =
            addProperty(applicationSettingsPanel, "Image Width", String.valueOf(settingsLoader.getImageWidth()));
        imageHeightProperty =
            addProperty(applicationSettingsPanel, "Image Height", String.valueOf(settingsLoader.getImageHeight()));


        generationSettingsPanel.setBackground(UIManager.getColor("Panel.background"));
        applicationSettingsPanel.setBackground(UIManager.getColor("Panel.background"));

        this.add(generationSettingsPanel);
        this.add(runtimeSettingsButton);
        this.add(applicationSettingsPanel);
        this.add(regenerateImageButton);
    }

    private Property addProperty(JPanel panel, String label, String value) {
        Property property = new Property(label);
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
    }
}
