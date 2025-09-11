package planner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for launching the GUI version of the program.
 */
public class Main extends Application {
    private final Responder estj = new PlannerResponder();

    /**
     * Initializes the GUI stage.
     *
     * @param stage primary stage supplied
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = loader.load();

            MainWindow controller = loader.getController();
            controller.setEstj(estj);

            stage.setTitle("iP (ESTJ)");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}