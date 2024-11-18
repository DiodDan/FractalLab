package backend.academy.ui.components;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Property extends JPanel {
    private final JTextField textField;

    public Property(String labelText, String tooltipText) {
        this.setLayout(new BorderLayout());

        JLabel label = new JLabel(labelText);
        label.setToolTipText(tooltipText);
        textField = new JTextField();

        this.add(label, BorderLayout.WEST);
        this.add(textField, BorderLayout.CENTER);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }
}
