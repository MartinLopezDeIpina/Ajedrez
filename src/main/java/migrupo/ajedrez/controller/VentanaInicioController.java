package migrupo.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import migrupo.ajedrez.view.ViewFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaInicioController implements Initializable {
    @FXML protected Pane inicioPane;
    @FXML protected Button iniciarSesionButton, registrarseButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML protected void onIniciarSesionButtonClicked(){
        ViewFactory.mostrarVentanaLog();
        cerrarVentana();
    }
    @FXML protected void onRegistrarseButtonClicked(){
        cerrarVentana();
        ViewFactory.mostrarVentanaRegistro();
    }

    private void cerrarVentana(){
        iniciarSesionButton.getScene().getWindow().hide();
    }
}
