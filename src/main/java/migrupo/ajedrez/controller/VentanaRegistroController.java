package migrupo.ajedrez.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import migrupo.ajedrez.model.BD.SimpleFactoryRegistro.FactoryRegistro;
import migrupo.ajedrez.model.BD.SimpleFactoryRegistro.Registro;
import migrupo.ajedrez.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaRegistroController implements Initializable {

    @FXML protected Button cancelarButton, registrarseButton;
    @FXML protected TextField nombreTextField, contrasenaTextField;

    FactoryRegistro mFactoryRegistro = FactoryRegistro.getInstance();

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

        Registro registro = intentarRegistro();


    }
    private Registro intentarRegistro(){
        return mFactoryRegistro.getRegistro(nombreTextField.getText(), contrasenaTextField.getText());
    }

}
