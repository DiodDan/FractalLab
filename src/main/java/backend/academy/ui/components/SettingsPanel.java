package backend.academy.ui.components;

import backend.academy.SettingsLoader;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;

public class SettingsPanel extends JPanel {
    private final Property imageWidthProperty;
    private final Property imageHeightProperty;
    private final Property generatorXMINProperty;
    private final Property generatorXMAXProperty;
    private final Property generatorYMINProperty;
    private final Property generatorYMAXProperty;
    private final Property generatorNonDrawMovesProperty;
    private final Property drawersAmountProperty;
    private final Property iterationsProperty;
    private final Property xBiasMinProperty;
    private final Property xBiasMaxProperty;
    private final Property yBiasMinProperty;
    private final Property yBiasMaxProperty;
    private final Property gammaProperty;
    private final Property maxThreadsProperty;
    private final JCheckBox imageFxApplyCheckbox;
    private final Property contrastProperty;
    private final Property imageWidthSaveProperty;
    private final Property imageHeightSaveProperty;
    private final JSlider scaleSlider;
    private final JSlider horizontalBiasSlider;
    private final JSlider verticalBiasSlider;
    private final SettingsLoader settings;
    private final int scaleDelimiter = 1000;

    public SettingsPanel(
        SettingsLoader settings,
        JButton runtimeSettingsButton,
        JButton regenerateImageButton
    ) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.settings = settings;

        // Settings that can be applied within one generation
        JPanel generationSettingsPanel = new JPanel(new GridLayout(0, 1));
        generationSettingsPanel.setBorder(BorderFactory.createTitledBorder("Runtime Applicable Settings"));

        gammaProperty = addProperty(
            generationSettingsPanel,
            "Gamma",
            String.valueOf(settings.getGamma()),
            "Gamma value"
        );
        imageFxApplyCheckbox = new JCheckBox("Apply Image FX", settings.isImageFxApply());
        generationSettingsPanel.add(imageFxApplyCheckbox);
        contrastProperty = addProperty(
            generationSettingsPanel,
            "Contrast",
            String.valueOf(settings.getImageContrast()),
            "Contrast value for image"
        );

        // Settings that affect the application
        JPanel applicationSettingsPanel = new JPanel(new GridLayout(0, 1));
        applicationSettingsPanel.setBorder(BorderFactory.createTitledBorder("Regenerate Image Settings"));

        applicationSettingsPanel.add(new JLabel("Scale"));
        scaleSlider = new JSlider(1, settings.getMaxScale(), (int) settings.getScale() * scaleDelimiter);
        applicationSettingsPanel.add(scaleSlider);
        scaleSlider.addChangeListener(e -> recalculateScaleAndBias());

        applicationSettingsPanel.add(new JLabel("horizontal Bias"));
        horizontalBiasSlider =
            new JSlider(settings.getLeftBound(), settings.getRightBound(), (int) settings.getHorizontalBias() * 1000);
        applicationSettingsPanel.add(horizontalBiasSlider);
        horizontalBiasSlider.addChangeListener(e -> recalculateScaleAndBias());

        applicationSettingsPanel.add(new JLabel("Vertical Bias"));
        verticalBiasSlider =
            new JSlider(settings.getBottomBound(), settings.getTopBound(), (int) settings.getVerticalBias() * 1000);
        applicationSettingsPanel.add(verticalBiasSlider);
        verticalBiasSlider.addChangeListener(e -> recalculateScaleAndBias());

        generatorXMINProperty = addProperty(
            applicationSettingsPanel,
            "Generator XMIN",
            String.valueOf(settings.getGeneratorXMIN()),
            "Minimum X value for the generator()"
        );
        generatorXMAXProperty = addProperty(
            applicationSettingsPanel,
            "Generator XMAX",
            String.valueOf(settings.getGeneratorXMAX()),
            "Maximum X value for the generator"
        );
        generatorYMINProperty = addProperty(
            applicationSettingsPanel,
            "Generator YMIN",
            String.valueOf(settings.getGeneratorYMIN()),
            "Minimum Y value for the generator"
        );
        generatorYMAXProperty = addProperty(
            applicationSettingsPanel,
            "Generator YMAX",
            String.valueOf(settings.getGeneratorYMAX()),
            "Maximum Y value for the generator"
        );
        generatorNonDrawMovesProperty = addProperty(
            applicationSettingsPanel,
            "Generator Non-Draw Moves",
            String.valueOf(settings.getGeneratorNonDrawMoves()),
            "Number of non-draw moves for the generator"
        );
        drawersAmountProperty = addProperty(
            applicationSettingsPanel,
            "Drawers Amount",
            String.valueOf(settings.getDrawersAmount()),
            "Amount of drawers"
        );
        iterationsProperty = addProperty(
            applicationSettingsPanel,
            "Iterations",
            String.valueOf(settings.getIterations()),
            "Number of iterations"
        );
        xBiasMinProperty = addProperty(
            applicationSettingsPanel,
            "X Bias Min",
            String.valueOf(settings.getXBiasMin()),
            "Minimum X bias"
        );
        xBiasMaxProperty = addProperty(
            applicationSettingsPanel,
            "X Bias Max",
            String.valueOf(settings.getXBiasMax()),
            "Maximum X bias"
        );
        yBiasMinProperty = addProperty(
            applicationSettingsPanel,
            "Y Bias Min",
            String.valueOf(settings.getYBiasMin()),
            "Minimum Y bias"
        );
        yBiasMaxProperty = addProperty(
            applicationSettingsPanel,
            "Y Bias Max",
            String.valueOf(settings.getYBiasMax()),
            "Maximum Y bias"
        );
        maxThreadsProperty = addProperty(
            applicationSettingsPanel,
            "Max Threads",
            String.valueOf(settings.getMaxDrawerThreads()),
            "Maximum number of threads"
        );

        imageWidthProperty = addProperty(
            applicationSettingsPanel,
            "Image Width generation",
            String.valueOf(settings.getImageWidth()),
            "Width of the image that will be generated"
        );
        imageHeightProperty = addProperty(
            applicationSettingsPanel,
            "Image Height generation",
            String.valueOf(settings.getImageHeight()),
            "Height of the image that will be generated"
        );

        imageWidthSaveProperty = addProperty(
            applicationSettingsPanel,
            "Image Width Saving",
            String.valueOf(settings.getImageWidth()),
            "Width of the image that will be Saving"
        );
        imageHeightSaveProperty = addProperty(
            applicationSettingsPanel,
            "Image Height Saving",
            String.valueOf(settings.getImageHeight()),
            "Height of the image that will be generated"
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

    private void recalculateScaleAndBias() {
        double scale = (double) (settings.getMaxScale() - scaleSlider.getValue()) / scaleDelimiter;
        double horizontalBias = (double) horizontalBiasSlider.getValue() / scaleDelimiter;
        double verticalBias = (double) verticalBiasSlider.getValue() / scaleDelimiter;

        generatorYMINProperty.setText(String.valueOf(-(scale + verticalBias)));
        generatorYMAXProperty.setText(String.valueOf(scale - verticalBias));
        generatorXMINProperty.setText(String.valueOf(-(scale + horizontalBias)));
        generatorXMAXProperty.setText(String.valueOf(scale - horizontalBias));
    }

    public void updateSettingsLoader(SettingsLoader settingsLoader) {
        settingsLoader.setImageWidth(Integer.parseInt(imageWidthProperty.getText()));
        settingsLoader.setImageHeight(Integer.parseInt(imageHeightProperty.getText()));
        settingsLoader.setGeneratorXMIN(Double.parseDouble(generatorXMINProperty.getText()));
        settingsLoader.setGeneratorXMAX(Double.parseDouble(generatorXMAXProperty.getText()));
        settingsLoader.setGeneratorYMIN(Double.parseDouble(generatorYMINProperty.getText()));
        settingsLoader.setGeneratorYMAX(Double.parseDouble(generatorYMAXProperty.getText()));
        settingsLoader.setGeneratorNonDrawMoves(Integer.parseInt(generatorNonDrawMovesProperty.getText()));
        settingsLoader.setDrawersAmount(Integer.parseInt(drawersAmountProperty.getText()));
        settingsLoader.setIterations(Integer.parseInt(iterationsProperty.getText()));
        settingsLoader.setXBiasMin(Double.parseDouble(xBiasMinProperty.getText()));
        settingsLoader.setXBiasMax(Double.parseDouble(xBiasMaxProperty.getText()));
        settingsLoader.setYBiasMin(Double.parseDouble(yBiasMinProperty.getText()));
        settingsLoader.setYBiasMax(Double.parseDouble(yBiasMaxProperty.getText()));
        settingsLoader.setGamma(Double.parseDouble(gammaProperty.getText()));
        settingsLoader.setMaxDrawerThreads(Integer.parseInt(maxThreadsProperty.getText()));
        settingsLoader.setImageFxApply(imageFxApplyCheckbox.isSelected());
        settingsLoader.setImageContrast(Float.parseFloat(contrastProperty.getText()));
        settingsLoader.setSaveWidth(Integer.parseInt(imageWidthSaveProperty.getText()));
        settingsLoader.setSaveHeight(Integer.parseInt(imageHeightSaveProperty.getText()));
    }
}
