package hirono;

import hirono.ui.component.DialogBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Hirono hirono;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image hironoImage = new Image(this.getClass().getResourceAsStream("/images/hirono.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the hirono instance */
    public void setHirono(Hirono h) {
        hirono = h;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing hirono's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = hirono.getResponse(input);

        // Check if response is an error (adjust condition based on your actual error format)
        boolean isError = response.toLowerCase().contains("error") || response.toLowerCase().contains("invalid");

        // Create a dialog box for Hirono's response
        DialogBox hironoDialog = DialogBox.getHironoDialog(response, hironoImage);

        // Apply error styling if it's an error message
        if (isError) {
            hironoDialog.getStyleClass().add("error-message");
        }

        // Add user input and Hirono response to dialog container
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                hironoDialog
        );

        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            closeWindow();
        }
    }


    private void closeWindow() {
        // Get the current stage and close it
        Stage stage = (Stage) dialogContainer.getScene().getWindow();
        stage.close();
    }
}
