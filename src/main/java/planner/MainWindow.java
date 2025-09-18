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

    private final Image userImage = new Image(getClass().getResourceAsStream("/images/MBTI.png"));

    private final Image dukeImage = new Image(getClass().getResourceAsStream("/images/ESTJ.png"));

    private Responder estj;

    /**
     * Wires the window to the responder logic.
     *
     * @param estj responder that generates replies
     */
    public void setEstj(Responder estj) {
        this.estj = estj;
    }

    /**
     * Initializes the logic
     *
     */
    @FXML
    public void initialize() {
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(
                        "Hi, my name is ESTJ. Try your first step to be a MBTI-J person!",
                        dukeImage
                )
        );
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        if (input.matches("(?i)^(todo|deadline|event)\\b.*")) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog("Nice plan!", dukeImage));
        } else if (input.matches("(?i)^mark\\s+\\d+\\b.*")) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog("Well done! Wish your planning habits helped you!", dukeImage));
        }

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