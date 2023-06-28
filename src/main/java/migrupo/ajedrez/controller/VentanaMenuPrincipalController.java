package migrupo.ajedrez.controller;

import javafx.beans.DefaultProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import migrupo.ajedrez.model.*;
import migrupo.ajedrez.model.BD.PartidaDAOImpl;
import migrupo.ajedrez.model.BD.UsuarioDAOImpl;
import migrupo.ajedrez.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaMenuPrincipalController implements Initializable {

    @FXML TextField textFieldNombre, textFieldNombreContrincante;
    @FXML TextArea textFieldMensaje;
    @FXML Button buttonCancelar, buttonCrearPartida, buttonPartidaNueva;
    @FXML Pane paneMenu, paneContrincante, paneMensaje;
    @FXML CheckBox checkBoxEsBot;

    Sesion mSesion;
    UsuarioDAOImpl mUsuarioDAOImpl;
    PartidaDAOImpl mPartidaDAOImpl;
    Partida mPartida;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarVariables();

        ocultarNecesario();

        ponerListennerNombre();
    }
    private void iniciarVariables() {
        mSesion = Sesion.getInstance();
        mUsuarioDAOImpl = UsuarioDAOImpl.getInstance();
        mPartidaDAOImpl = PartidaDAOImpl.getInstance();
        mPartida = Partida.getInstance();
    }
    private void ocultarNecesario(){
        paneContrincante.setVisible(false);
        paneMensaje.setVisible(false);
        paneMenu.setVisible(true);
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

    @FXML protected void onButtonPartidaNuevaClicked(){
        paneMenu.setVisible(false);
        paneContrincante.setVisible(true);
    }

    @FXML protected void onButtonCancelarClicked(){
        paneContrincante.setVisible(false);
        paneMenu.setVisible(true);
    }
    @FXML protected void onButtonCrearPartidaClicked(){
        if(existeContrincante()){
            crearPartida();
            pasarAJuego();
        }else{
            mostrarError("El jugador no existe");
        }
    }
    private boolean existeContrincante(){
        String nombreContrincante = textFieldNombreContrincante.getText();
        return mUsuarioDAOImpl.existeJugador(nombreContrincante) && !contrincanteEsJugadorActual();
    }
    private boolean contrincanteEsJugadorActual(){
        return textFieldNombreContrincante.getText().equals(mSesion.getJugador().getNombreValue());
    }
    private void crearPartida(){

        Jugador jugador1 = mSesion.getJugador();
        Usuario jugador2 = getContrincante();

        int numPartida = mPartidaDAOImpl.registrarPartida(jugador1, jugador2);

        mPartida.setPartida(numPartida, jugador1, jugador2);
    }
    private Usuario getContrincante(){
        String nombreJugador2 = textFieldNombreContrincante.getText();

        if(checkBoxEsBot.isSelected()){
            return new Bot(nombreJugador2, mUsuarioDAOImpl.getContrasena(nombreJugador2));
        }else{
            return new Jugador(nombreJugador2, mUsuarioDAOImpl.getContrasena(nombreJugador2));
        }
    }
    private void pasarAJuego(){
        cerrarVentana();
        ViewFactory.mostrarVentanaJuego();
    }
    @FXML private void mostrarError(String mensaje){
        paneContrincante.setVisible(false);
        textFieldMensaje.setText("El jugador indicado no existe");
        paneMensaje.setVisible(true);
    }

    @FXML protected void onButtonAceptarClicked(){
        paneMensaje.setVisible(false);
        paneContrincante.setVisible(true);
    }

    @FXML protected void onButtonContinuarPartidaClicked(){

    }
    @FXML protected void onButtonVerPartidaClicked(){

    }


}
