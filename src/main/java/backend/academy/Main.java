package backend.academy;

import backend.academy.ui.MainApplication;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        MainApplication app = new MainApplication();
        app.runApp();
    }
}
