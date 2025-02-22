package hirono;

import java.io.IOException;

import hirono.exception.HironoException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class MainApp extends Application {

    private Hirono hirono;

    @Override
    public void start(Stage stage) throws IOException, HironoException {
        hirono = new Hirono();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Hirono - Your Personal Task Manager");
            stage.setScene(scene);
            stage.setResizable(true);
            fxmlLoader.<MainWindow>getController().setHirono(hirono);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
