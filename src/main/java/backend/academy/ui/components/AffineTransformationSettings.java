package backend.academy.ui.components;

import backend.academy.SettingsLoader;
import backend.academy.entityes.AffineTransformation;
import backend.academy.entityes.Pixel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AffineTransformationSettings extends JPanel {

    private final JTextField numberOfTransformationsField;
    private final JButton autoGenerateButton;
    private final JButton addTransformationButton;
    private final JPanel transformationsPanel;
    private final List<AffineTransformationComponent> transformationComponents;
    private JCheckBox themedColorCheckbox;
    private JTextField variationsField;

    public AffineTransformationSettings(SettingsLoader settingsLoader) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 300)); // Set fixed size

        numberOfTransformationsField = new JTextField(String.valueOf(settingsLoader.getAffineTransformationsAmount()));
        autoGenerateButton = new JButton("Auto-generate");
        addTransformationButton = new JButton("Add One");
        JButton applyButton = new JButton("Apply");

        add(createTopPanel(), BorderLayout.NORTH);
        transformationsPanel = new JPanel();
        transformationsPanel.setLayout(new BoxLayout(transformationsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(transformationsPanel), BorderLayout.CENTER);
        add(createColorSettingsPanel(settingsLoader), BorderLayout.WEST);
        add(applyButton, BorderLayout.SOUTH);

        transformationComponents = new ArrayList<>();

        // Action Listeners
        autoGenerateButton.addActionListener(e -> autoGenerateTransformations(settingsLoader));
        applyButton.addActionListener(e -> applyChanges(settingsLoader));
        addTransformationButton.addActionListener(e -> addOneTransformation(settingsLoader));

        // Initialize with auto-generated transformations
        autoGenerateButton.doClick();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(new JLabel("Number of Transformations:"));
        topPanel.add(numberOfTransformationsField);
        topPanel.add(autoGenerateButton);
        topPanel.add(addTransformationButton);
        return topPanel;
    }

    private JPanel createColorSettingsPanel(SettingsLoader settingsLoader) {
        JPanel colorSettingsPanel = new JPanel(new GridLayout(4, 2));

        themedColorCheckbox = new JCheckBox("Use Themed Colors", settingsLoader.isUseThemedColorGeneration());
        variationsField = new JTextField(String.valueOf(settingsLoader.getColorVariation()));

        JButton[] colorButtons = createColorButtons(settingsLoader);

        colorSettingsPanel.add(new JLabel("Use Themed Colors:"));
        colorSettingsPanel.add(themedColorCheckbox);
        colorSettingsPanel.add(new JLabel("Color Variations:"));
        colorSettingsPanel.add(variationsField);

        for (JButton colorButton : colorButtons) {
            colorSettingsPanel.add(colorButton);
        }

        return colorSettingsPanel;
    }

    private JButton[] createColorButtons(SettingsLoader settingsLoader) {
        JButton[] colorButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            final int colorIndex = i;
            colorButtons[i] = new JButton("Color " + (i + 1));
            colorButtons[i].setBackground(settingsLoader.getThemColors()[i]);
            int finalI = i;
            colorButtons[i].addActionListener(e -> updateColor(settingsLoader, colorIndex, colorButtons[finalI]));
        }
        return colorButtons;
    }

    private void updateColor(SettingsLoader settingsLoader, int colorIndex, JButton colorButton) {
        Color chosenColor = JColorChooser.showDialog(this, "Choose Color", settingsLoader.getThemColors()[colorIndex]);
        if (chosenColor != null) {
            settingsLoader.getThemColors()[colorIndex] = chosenColor;
            colorButton.setBackground(chosenColor);
        }
    }

    private void autoGenerateTransformations(SettingsLoader settingsLoader) {
        try {
            int transformationCount = Integer.parseInt(numberOfTransformationsField.getText());
            settingsLoader.setAffineTransformationsAmount(transformationCount);
            updateSettingsLoaderColors(settingsLoader);
            settingsLoader.generateAffineTransformations();
            displayTransformations(settingsLoader);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for transformations.", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOneTransformation(SettingsLoader settingsLoader) {
        settingsLoader.getAffineTransformations().add(AffineTransformation.getRandom(settingsLoader));
        displayTransformations(settingsLoader);
    }

    private void applyChanges(SettingsLoader settingsLoader) {
        List<AffineTransformation> transformations = new ArrayList<>();
        for (AffineTransformationComponent component : transformationComponents) {
            if (!component.isDeleted) {
                transformations.add(new AffineTransformation(
                    component.getA(),
                    component.getB(),
                    component.getC(),
                    component.getD(),
                    component.getE(),
                    component.getF(),
                    new Pixel(component.getColor())
                ));
            }
        }
        settingsLoader.setAffineTransformations(transformations);
        settingsLoader.setUseThemedColorGeneration(themedColorCheckbox.isSelected());
    }

    private void displayTransformations(SettingsLoader settingsLoader) {
        transformationsPanel.removeAll();
        transformationComponents.clear();

        for (AffineTransformation transformation : settingsLoader.getAffineTransformations()) {
            AffineTransformationComponent component = new AffineTransformationComponent(transformation);
            transformationComponents.add(component);
            transformationsPanel.add(component);
        }

        transformationsPanel.revalidate();
        transformationsPanel.repaint();
    }

    private void updateSettingsLoaderColors(SettingsLoader settingsLoader) {
        settingsLoader.setUseThemedColorGeneration(themedColorCheckbox.isSelected());
        try {
            settingsLoader.setColorVariation(Integer.parseInt(variationsField.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid color variation value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
