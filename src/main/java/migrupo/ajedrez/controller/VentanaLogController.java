package migrupo.ajedrez.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import migrupo.ajedrez.view.ViewFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaLogController implements Initializable {

    @FXML protected Button iniciarSesionButton, cancelarButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML protected void onCancelarButtonClicked() {
        ViewFactory.mostrarVentanaInicio();
        cerrarVentana();
    }
    private void cerrarVentana(){
        cancelarButton.getScene().getWindow().hide();
    }

    @FXML protected void onAceptarButtonClicked() {

    }
}
