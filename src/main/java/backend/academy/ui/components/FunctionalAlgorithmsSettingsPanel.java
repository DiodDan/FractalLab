package backend.academy.ui.components;

import backend.academy.SettingsLoader;
import backend.academy.generators.transformations.Transformation;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionalAlgorithmsSettingsPanel extends JPanel {
    private HashMap<String, Transformation> transformations = new HashMap<>();

    public FunctionalAlgorithmsSettingsPanel(SettingsLoader settingsLoader) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel functionalAlgorithmsPanel = new JPanel(new GridLayout(0, 1));
        this.setBorder(BorderFactory.createTitledBorder("Transformation Algorithms"));

        for (Transformation transformation : settingsLoader.getAvailableFunctionalTransformations()) {
            transformations.put(transformation.getFancyName(), transformation);
            addOption(transformation);
        }

        this.add(functionalAlgorithmsPanel);
    }

    public void updateSettingsLoader(SettingsLoader settingsLoader) {
        List<Transformation> checkedTransformations = new ArrayList<>();
        for (Component component : this.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    checkedTransformations.add(transformations.get(checkBox.getText()));
                }
            }
        }
        if (checkedTransformations.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You should select at least one transformation.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        settingsLoader.setFunctionalTransformations(checkedTransformations);
    }

    private void addOption(Transformation algorithm) {
        JCheckBox checkBox = new JCheckBox(algorithm.getFancyName());
        this.add(checkBox);
    }

}
