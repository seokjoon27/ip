package planner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for the main window.
 */
public class MainWindow {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private final Image userImage = new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dukeImage = new Image(getClass().getResourceAsStream("/images/DaDuke.png"));

    private Responder estj;

    /**
     * Wires the window to the responder logic.
     *
     * @param estj responder that generates replies
     */
    public void setEstj(Responder estj) {
        this.estj = estj;
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) return;

        String reply;
        try {
            reply = estj.getResponse(input);
        } catch (Exception e) {
            reply = "Error: " + e.getMessage();
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(reply, dukeImage)
        );

        userInput.clear();

        if ("bye".equalsIgnoreCase(input.trim())) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}