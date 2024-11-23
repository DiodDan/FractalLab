package backend.academy.ui.components;

import backend.academy.SettingsLoader;
import backend.academy.generators.transformations.Transformation;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FunctionalAlgorithmsSettingsPanel extends JPanel {
    private final HashMap<String, Transformation> transformations = new HashMap<>();

    public FunctionalAlgorithmsSettingsPanel(SettingsLoader settingsLoader) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel functionalAlgorithmsPanel = new JPanel(new GridLayout(0, 1));
        this.setBorder(BorderFactory.createTitledBorder("Transformation Algorithms"));

        List<Transformation> availableTransformations = settingsLoader.getAvailableFunctionalTransformations();
        for (int i = 0; i < availableTransformations.size(); i++) {
            Transformation transformation = availableTransformations.get(i);
            transformations.put(transformation.getFancyName(), transformation);
            addOption(transformation, i == availableTransformations.size() - 1);
        }
        this.add(functionalAlgorithmsPanel);
    }

    public void updateSettingsLoader(SettingsLoader settingsLoader) {
        List<Transformation> checkedTransformations = new ArrayList<>();
        for (Component component : this.getComponents()) {
            if (component instanceof JCheckBox checkBox) {
                if (checkBox.isSelected()) {
                    checkedTransformations.add(transformations.get(checkBox.getText()));
                }
            }
        }
        if (checkedTransformations.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "You should select at least one transformation.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        settingsLoader.setFunctionalTransformations(checkedTransformations);
    }

    private void addOption(Transformation algorithm, boolean isLast) {
        JCheckBox checkBox = new JCheckBox(algorithm.getFancyName());
        checkBox.setSelected(isLast);
        this.add(checkBox);
    }

}
