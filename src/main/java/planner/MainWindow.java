package planner;

import java.util.Locale;
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
        dialogContainer.heightProperty().addListener((obs, oldV, newV) -> {
            scrollPane.setVvalue(1.0);
        });
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        String reply;
        try {
            reply = estj.getResponse(input);
        } catch (Exception e) {
            reply = "Error: " + e.getMessage();
        }

        String prefix = null;

        String in = input.trim().toLowerCase(Locale.ROOT);
        String out = reply.trim().toLowerCase(Locale.ROOT);

        if (in.startsWith("todo ") || in.startsWith("deadline ") || in.startsWith("event ")) {
            boolean addSuccess = out.startsWith("added:");
            if (addSuccess) {
                prefix = "Nice plan!";
            }
        }

        if (prefix == null && in.startsWith("mark ")) {
            boolean looksOk = !out.startsWith("error:") && !out.startsWith("usage:");
            boolean successKeyword = out.contains("marked") || out.contains("done") || out.contains("completed");
            if (looksOk && successKeyword) {
                prefix = "Well done! Wish your planning habits helped you!";
            }
        }

        if (prefix != null) {
            reply = prefix + System.lineSeparator() + reply;
        }

        dialogContainer.getChildren().add(DialogBox.getDukeDialog(reply, dukeImage));

        userInput.clear();
        if ("bye".equalsIgnoreCase(input.trim())) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}