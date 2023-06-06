package migrupo.ajedrez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import migrupo.ajedrez.view.ViewFactory;

import java.io.IOException;

public class AjedrezApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory.mostrarVentanaLog();
    }

    public static void main(String[] args) {
        launch();
    }
}