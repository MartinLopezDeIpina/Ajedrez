package migrupo.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaLogController implements Initializable {
    @FXML protected Pane inicioPane, datosPane;
    @FXML protected Button iniciarSesionButton, registrarseButton, aceptarButton, cancelarButton;
    @FXML protected TextField nombreTextField, contrasenaTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datosPane.setVisible(false);
        inicioPane.setVisible(true);
    }

    @FXML protected void onIniciarSesionButtonClicked(){
        iniciarPanelDatos();
        setNombreBotonAceptar("Iniciar Sesión");
    }
    @FXML protected void onRegistrarseButtonClicked(){
        iniciarPanelDatos();
        setNombreBotonAceptar("Registrarse");
    }
    private void setNombreBotonAceptar(String nombre){
        aceptarButton.setText(nombre);
    }

    private void iniciarPanelDatos(){
        inicioPane.setVisible(false);
        datosPane.setVisible(true);

        limpiarTextos();
    }
    private void limpiarTextos(){
        nombreTextField.clear();
        contrasenaTextField.clear();
    }


    @FXML protected void onCancelarButtonClicked(){
        datosPane.setVisible(false);
        inicioPane.setVisible(true);
    }
    @FXML protected void onAceptarButtonClicked(){

    }

}
