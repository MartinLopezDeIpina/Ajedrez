package migrupo.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import migrupo.ajedrez.model.Sesion;
import migrupo.ajedrez.model.Usuario;
import migrupo.ajedrez.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaMenuPrincipalController implements Initializable {

    @FXML TextField textFieldNombre;

    Sesion mSesion = Sesion.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ponerListennerNombre();
    }

    private void ponerListennerNombre(){
        mSesion.getJugador().getNombre().addListener(((observable, oldValue, newValue) -> {
            textFieldNombre.setText(newValue);
        }));
        textFieldNombre.setText(mSesion.getJugador().getNombreValue());
    }

    @FXML protected void onCerrarSesionButtonClicked(){
        mSesion.cerrarSesion();
        ViewFactory.mostrarVentanaInicio();
        cerrarVentana();
    }

    private void cerrarVentana() {
        textFieldNombre.getScene().getWindow().hide();
    }
}
