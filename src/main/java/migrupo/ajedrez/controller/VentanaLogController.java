package migrupo.ajedrez.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion.Autenticacion;
import migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion.FactoryAutenticador;
import migrupo.ajedrez.view.ViewFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaLogController implements Initializable {

    @FXML protected Button iniciarSesionButton, cancelarButton;
    @FXML protected TextField textNombre, textContrasena;
    @FXML protected TextArea textMensaje;
    @FXML protected Pane paneDatos, paneMensaje;

    FactoryAutenticador  mFactoryAutenticador = FactoryAutenticador.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    @FXML protected void onButtonIniciarSesionClicked() {

        Autenticacion autenticacion = getAutenticacion();

        if(autenticacion.isAutenticado()){
            cerrarVentana();
            ViewFactory.mostrarVentanaMenuPrincipal();
        }

        mostrarError(autenticacion.getMensajeAutenticacion());

    }
    private Autenticacion getAutenticacion(){
        return mFactoryAutenticador.generarAutenticador(textNombre.getText(), textContrasena.getText());
    }
    private void mostrarError(String mensaje){
        paneDatos.setVisible(false);

        textMensaje.setText(mensaje);
        paneMensaje.setVisible(true);
    }

    @FXML protected void onButtonAceptarClicked(){
        paneMensaje.setVisible(false);
        paneDatos.setVisible(true);
    }
}
