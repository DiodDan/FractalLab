package backend.academy.ui.components;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JLabel;

class PropertyTest {

    private Property property;

    @BeforeEach
    void setUp() {
        property = new Property("Label", "Tooltip");
    }

    @Test
    void constructor_shouldInitializeComponentsCorrectly() {
        assertNotNull(property);
        assertEquals("Label", ((JLabel) property.getComponent(0)).getText());
        assertEquals("Tooltip", ((JLabel) property.getComponent(0)).getToolTipText());
    }

    @Test
    void getText_shouldReturnCorrectText() {
        property.setText("Test Text");
        assertEquals("Test Text", property.getText());
    }

    @Test
    void setText_shouldUpdateTextFieldCorrectly() {
        property.setText("New Text");
        assertEquals("New Text", property.getText());
    }

    @Test
    void setText_shouldHandleEmptyString() {
        property.setText("");
        assertEquals("", property.getText());
    }

    @Test
    void setText_shouldHandleNull() {
        property.setText(null);
        assertEquals("", property.getText());
    }
}
