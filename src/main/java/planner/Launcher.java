package planner;

import javafx.application.Application;

/**
 * A entry point class.
 * Delegates directly to {@link Main}.
 */
public class Launcher {
    /**
     * Delegates to {@link Main}.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}