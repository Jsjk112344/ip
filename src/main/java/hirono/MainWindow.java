package hirono;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import hirono.ui.component.DialogBox;

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

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpeg"));
    private Image hironoImage = new Image(this.getClass().getResourceAsStream("/images/hirono.jpeg"));

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
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getHironoDialog(response, hironoImage)
        );
        userInput.clear();
    }
}
