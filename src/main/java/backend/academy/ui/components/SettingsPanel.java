package backend.academy.ui.components;

import backend.academy.SettingsLoader;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;

public class SettingsPanel extends JPanel {

    public static final String PANEL_BACKGROUND = "Panel.background";

    // Константы для строк
    private static final String GENERATOR_XMIN = "Generator XMIN";
    private static final String GENERATOR_XMAX = "Generator XMAX";
    private static final String GENERATOR_YMIN = "Generator YMIN";
    private static final String GENERATOR_YMAX = "Generator YMAX";
    private static final String GENERATOR_NON_DRAW_MOVES = "Generator Non-Draw Moves";
    private static final String DRAWERS_AMOUNT = "Drawers Amount";
    private static final String ITERATIONS = "Iterations";
    private static final String X_BIAS_MIN = "X Bias Min";
    private static final String X_BIAS_MAX = "X Bias Max";
    private static final String Y_BIAS_MIN = "Y Bias Min";
    private static final String Y_BIAS_MAX = "Y Bias Max";
    private static final String MAX_THREADS = "Max Threads";
    private static final String IMAGE_WIDTH_GENERATION = "Image Width generation";
    private static final String IMAGE_HEIGHT_GENERATION = "Image Height generation";
    private static final String IMAGE_WIDTH_SAVING = "Image Width Saving";
    private static final String IMAGE_HEIGHT_SAVING = "Image Height Saving";

    private final Map<String, Property> properties = new HashMap<>();
    private final JCheckBox imageFxApplyCheckbox;
    private final JSlider scaleSlider;
    private final JSlider horizontalBiasSlider;
    private final JSlider verticalBiasSlider;
    private final SettingsLoader settings;
    private static final int SCALE_DELIMITER = 1000;

    public SettingsPanel(SettingsLoader settings, JButton runtimeSettingsButton, JButton regenerateImageButton) {
        this.settings = settings;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Панели для настроек
        JPanel generationSettingsPanel = createTitledPanel("Runtime Applicable Settings");
        JPanel applicationSettingsPanel = createTitledPanel("Regenerate Image Settings");

        // Добавляем настройки
        addProperty(generationSettingsPanel, "Gamma", String.valueOf(settings.getGamma()), "Gamma value");
        imageFxApplyCheckbox = new JCheckBox("Apply Image FX", settings.isImageFxApply());
        generationSettingsPanel.add(imageFxApplyCheckbox);
        addProperty(generationSettingsPanel, "Contrast", String.valueOf(settings.getImageContrast()), "Contrast value");

        scaleSlider = createSlider(applicationSettingsPanel, "Scale", 1, settings.getMaxScale(),
            (int) settings.getScale() * SCALE_DELIMITER);
        horizontalBiasSlider = createSlider(applicationSettingsPanel, "Horizontal Bias",
            settings.getLeftBound(), settings.getRightBound(),
            (int) settings.getHorizontalBias() * SCALE_DELIMITER);
        verticalBiasSlider = createSlider(applicationSettingsPanel, "Vertical Bias",
            settings.getBottomBound(), settings.getTopBound(),
            (int) settings.getVerticalBias() * SCALE_DELIMITER);

        addGeneratorSettings(applicationSettingsPanel);
        addImageSettings(applicationSettingsPanel);

        generationSettingsPanel.setBackground(UIManager.getColor(PANEL_BACKGROUND));
        applicationSettingsPanel.setBackground(UIManager.getColor(PANEL_BACKGROUND));

        this.add(generationSettingsPanel);
        this.add(runtimeSettingsButton);
        this.add(applicationSettingsPanel);
        this.add(regenerateImageButton);

        scaleSlider.addChangeListener(e -> recalculateScaleAndBias());
        horizontalBiasSlider.addChangeListener(e -> recalculateScaleAndBias());
        verticalBiasSlider.addChangeListener(e -> recalculateScaleAndBias());
    }

    private JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    private JSlider createSlider(JPanel panel, String label, int min, int max, int value) {
        panel.add(new JLabel(label));
        JSlider slider = new JSlider(min, max, value);
        panel.add(slider);
        return slider;
    }

    private void addGeneratorSettings(JPanel panel) {
        addProperty(panel, GENERATOR_XMIN,
            String.valueOf(settings.getGeneratorXMIN()), "Minimum X value for the generator");
        addProperty(panel, GENERATOR_XMAX,
            String.valueOf(settings.getGeneratorXMAX()), "Maximum X value for the generator");
        addProperty(panel, GENERATOR_YMIN,
            String.valueOf(settings.getGeneratorYMIN()), "Minimum Y value for the generator");
        addProperty(panel, GENERATOR_YMAX,
            String.valueOf(settings.getGeneratorYMAX()), "Maximum Y value for the generator");
        addProperty(panel, GENERATOR_NON_DRAW_MOVES,
            String.valueOf(settings.getGeneratorNonDrawMoves()), "Number of non-draw moves");
        addProperty(panel, DRAWERS_AMOUNT,
            String.valueOf(settings.getDrawersAmount()), "Amount of drawers");
        addProperty(panel, ITERATIONS,
            String.valueOf(settings.getIterations()), "Number of iterations");
        addProperty(panel, X_BIAS_MIN,
            String.valueOf(settings.getXBiasMin()), "Minimum X bias");
        addProperty(panel, X_BIAS_MAX,
            String.valueOf(settings.getXBiasMax()), "Maximum X bias");
        addProperty(panel, Y_BIAS_MIN,
            String.valueOf(settings.getYBiasMin()), "Minimum Y bias");
        addProperty(panel, Y_BIAS_MAX,
            String.valueOf(settings.getYBiasMax()), "Maximum Y bias");
        addProperty(panel, MAX_THREADS,
            String.valueOf(settings.getMaxDrawerThreads()), "Maximum number of threads");
    }

    private void addImageSettings(JPanel panel) {
        addProperty(panel, IMAGE_WIDTH_GENERATION, String.valueOf(settings.getImageWidth()),
            "Width of the generated image");
        addProperty(panel, IMAGE_HEIGHT_GENERATION, String.valueOf(settings.getImageHeight()),
            "Height of the generated image");
        addProperty(panel, IMAGE_WIDTH_SAVING, String.valueOf(settings.getSaveWidth()), "Width of the saved image");
        addProperty(panel, IMAGE_HEIGHT_SAVING, String.valueOf(settings.getSaveHeight()), "Height of the saved image");
    }

    private void addProperty(JPanel panel, String label, String value, String tooltip) {
        Property property = new Property(label, tooltip);
        property.setText(value);
        properties.put(label, property);
        panel.add(property);
    }

    private void recalculateScaleAndBias() {
        double scale = (double) (settings.getMaxScale() - scaleSlider.getValue()) / SCALE_DELIMITER;
        double horizontalBias = (double) horizontalBiasSlider.getValue() / SCALE_DELIMITER;
        double verticalBias = (double) verticalBiasSlider.getValue() / SCALE_DELIMITER;

        properties.get(GENERATOR_XMIN).setText(String.valueOf(-(scale + horizontalBias)));
        properties.get(GENERATOR_XMAX).setText(String.valueOf(scale - horizontalBias));
        properties.get(GENERATOR_YMIN).setText(String.valueOf(-(scale + verticalBias)));
        properties.get(GENERATOR_YMAX).setText(String.valueOf(scale - verticalBias));
    }

    public void updateSettingsLoader(SettingsLoader settingsLoader) {
        updateGeneratorSettings(settingsLoader);
        updateImageSettings(settingsLoader);
        settingsLoader.setImageFxApply(imageFxApplyCheckbox.isSelected());
    }

    private void updateGeneratorSettings(SettingsLoader settingsLoader) {
        settingsLoader.setGeneratorXMIN(Double.parseDouble(properties.get(GENERATOR_XMIN).getText()));
        settingsLoader.setGeneratorXMAX(Double.parseDouble(properties.get(GENERATOR_XMAX).getText()));
        settingsLoader.setGeneratorYMIN(Double.parseDouble(properties.get(GENERATOR_YMIN).getText()));
        settingsLoader.setGeneratorYMAX(Double.parseDouble(properties.get(GENERATOR_YMAX).getText()));
        settingsLoader.setGeneratorNonDrawMoves(Integer.parseInt(properties.get(GENERATOR_NON_DRAW_MOVES).getText()));
        settingsLoader.setDrawersAmount(Integer.parseInt(properties.get(DRAWERS_AMOUNT).getText()));
        settingsLoader.setIterations(Integer.parseInt(properties.get(ITERATIONS).getText()));
        settingsLoader.setXBiasMin(Double.parseDouble(properties.get(X_BIAS_MIN).getText()));
        settingsLoader.setXBiasMax(Double.parseDouble(properties.get(X_BIAS_MAX).getText()));
        settingsLoader.setYBiasMin(Double.parseDouble(properties.get(Y_BIAS_MIN).getText()));
        settingsLoader.setYBiasMax(Double.parseDouble(properties.get(Y_BIAS_MAX).getText()));
        settingsLoader.setMaxDrawerThreads(Integer.parseInt(properties.get(MAX_THREADS).getText()));
    }

    private void updateImageSettings(SettingsLoader settingsLoader) {
        settingsLoader.setImageWidth(Integer.parseInt(properties.get(IMAGE_WIDTH_GENERATION).getText()));
        settingsLoader.setImageHeight(Integer.parseInt(properties.get(IMAGE_HEIGHT_GENERATION).getText()));
        settingsLoader.setSaveWidth(Integer.parseInt(properties.get(IMAGE_WIDTH_SAVING).getText()));
        settingsLoader.setSaveHeight(Integer.parseInt(properties.get(IMAGE_HEIGHT_SAVING).getText()));
    }
}
