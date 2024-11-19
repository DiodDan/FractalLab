package backend.academy.ui.components;

import backend.academy.entityes.AffineTransformation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = false)
public class AffineTransformationComponent extends JPanel {
    private JTextField aField;
    private JTextField bField;
    private JTextField cField;
    private JTextField dField;
    private JTextField eField;
    private JTextField fField;
    private final JButton colorButton;
    private final JPopupMenu settingsMenu = new JPopupMenu();
    @Getter private Color color;
    public boolean isDeleted = false;

    public AffineTransformationComponent(AffineTransformation transformation) {
        this.setLayout(new GridLayout(1, 3));

        color = transformation.getPixel().getColor();

        colorButton = new JButton("Choose Color");
        colorButton.addActionListener(e -> chooseColor());
        colorButton.setBackground(color);
        this.add(colorButton, BorderLayout.CENTER);

        JButton settingsButton = new JButton("Settings");
        setupSettingsPopup(transformation);
        settingsButton.addActionListener(e -> settingsMenu.show(this, 0, this.getHeight()));
        this.add(settingsButton, BorderLayout.SOUTH);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.RED);
        deleteButton.addActionListener(e -> deleteTransformation());
        this.add(deleteButton, BorderLayout.SOUTH);
    }

    private void setupSettingsPopup(AffineTransformation transformation) {
        aField = addFieldToMenu(settingsMenu, "a", transformation.a);
        bField = addFieldToMenu(settingsMenu, "b", transformation.b);
        cField = addFieldToMenu(settingsMenu, "c", transformation.c);
        dField = addFieldToMenu(settingsMenu, "d", transformation.d);
        eField = addFieldToMenu(settingsMenu, "e", transformation.e);
        fField = addFieldToMenu(settingsMenu, "f", transformation.f);

        settingsMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                // No action needed when the menu becomes visible
            }

            @Override
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
                saveChanges(transformation);
            }

            @Override
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
                // No action needed when the menu is canceled
            }
        });
    }

    private JTextField addFieldToMenu(JPopupMenu menu, String label, double value) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField(String.valueOf(value));
        panel.add(jLabel);
        panel.add(textField);
        menu.add(panel);
        return textField;
    }

    private void deleteTransformation() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this transformation?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            Container parent = this.getParent();
            isDeleted = true;
            if (parent != null) {
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
            }
        }
    }

    private void saveChanges(AffineTransformation transformation) {
        transformation.a = getA();
        transformation.b = getB();
        transformation.c = getC();
        transformation.d = getD();
        transformation.e = getE();
        transformation.f = getF();
    }

    private JTextField addField(String label) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField();
        this.add(jLabel);
        this.add(textField);
        return textField;
    }

    private void chooseColor() {
        Color TempColor = JColorChooser.showDialog(this, "Color", color);
        color = TempColor != null ? TempColor : color;
        if (color != null) {
            colorButton.setBackground(color);
        }
    }

    public double getA() {
        return Double.parseDouble(aField.getText());
    }

    public double getB() {
        return Double.parseDouble(bField.getText());
    }

    public double getC() {
        return Double.parseDouble(cField.getText());
    }

    public double getD() {
        return Double.parseDouble(dField.getText());
    }

    public double getE() {
        return Double.parseDouble(eField.getText());
    }

    public double getF() {
        return Double.parseDouble(fField.getText());
    }
}
