package hirono;

import javafx.application.Application;

/**
 * A launcher class to workaround module-related JavaFX issues in Java 11+.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}
