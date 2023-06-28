package migrupo.ajedrez.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import migrupo.ajedrez.model.BD.SimpleFactoryRegistro.FactoryRegistro;
import migrupo.ajedrez.model.BD.SimpleFactoryRegistro.Registro;
import migrupo.ajedrez.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaRegistroController implements Initializable {

    @FXML protected Button cancelarButton, registrarseButton, ButtonAceptar;
    @FXML protected TextField nombreTextField, contrasenaTextField;
    @FXML protected TextArea textMensaje;
    @FXML protected Pane paneDatos, paneMensaje;

    FactoryRegistro mFactoryRegistro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mFactoryRegistro = FactoryRegistro.getInstance();

        paneMensaje.setVisible(false);
        paneDatos.setVisible(true);

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

        if(registro.getRegistrado()){
            ViewFactory.mostrarVentanaLog();
            cerrarVentana();
        }

        mostrarError(registro.getMensaje());

    }
    private Registro intentarRegistro(){
        return mFactoryRegistro.getRegistro(nombreTextField.getText(), contrasenaTextField.getText());
    }
    private void mostrarError(String mensaje){
        textMensaje.setText(mensaje);

        paneMensaje.setVisible(true);
        paneDatos.setVisible(false);
    }

    @FXML protected void onButtonAceptarClicked(){
        paneMensaje.setVisible(false);
        paneDatos.setVisible(true);
    }


}
