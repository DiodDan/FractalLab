package backend.academy.ui.components;

import javax.swing.*;
import java.awt.*;

public class Property extends JPanel {
    private JLabel label;
    private JTextField textField;

    public Property(String labelText) {
        this.setLayout(new BorderLayout());

        label = new JLabel(labelText);
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

    public JLabel getLabel() {
        return label;
    }

    public JTextField getTextField() {
        return textField;
    }
}
