package migrupo.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import migrupo.ajedrez.model.*;
import migrupo.ajedrez.model.BD.PartidaDAOImpl;
import migrupo.ajedrez.model.BD.UsuarioDAOImpl;
import migrupo.ajedrez.view.ViewFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VentanaMenuPrincipalController implements Initializable {

    @FXML TextField textFieldNombre, textFieldNombreContrincante;
    @FXML TextArea textFieldMensaje;
    @FXML Button buttonCancelar, buttonCrearPartida, buttonPartidaNueva, buttonAceptarElegirPartida, buttonCancelarElegirPartida;
    @FXML Pane paneMenu, paneContrincante, paneMensaje, paneElegirPartida;
    @FXML CheckBox checkBoxEsBot;
    @FXML ComboBox comboBoxElegirPartida;

    Sesion mSesion;
    UsuarioDAOImpl mUsuarioDAOImpl;
    PartidaDAOImpl mPartidaDAOImpl;
    Partida mPartida;

    private boolean seguirPartida = false;
    private static boolean verPartida = false;

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
        paneElegirPartida.setVisible(false);
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
        setVerPartida(false);
        paneMenu.setVisible(false);
        paneContrincante.setVisible(true);
    }

    @FXML protected void onButtonCancelarClicked(){
        paneContrincante.setVisible(false);
        paneMenu.setVisible(true);
    }
    @FXML protected void onButtonCrearPartidaClicked(){
        if(existeContrincante()){
            setVerPartida(false);
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

        mPartida.iniciarPartidaNueva(jugador1, jugador2);
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
        seguirPartida = true;

        anadirPartidasComboBox(false);

        mostrarElegirPartida();
    }

    private void anadirPartidasComboBox(boolean acabadas) {
        List<String[]> partidas = acabadas ? mPartidaDAOImpl.getPartidasAcabadas(mSesion.getJugador().getNombreValue()) : mPartidaDAOImpl.getPartidasSinAcabar(mSesion.getJugador().getNombreValue());
        partidas.stream().forEach(partida -> comboBoxElegirPartida.getItems().add(String.format("(%s) %sº , contrincante: %s, %s"
                ,Integer.parseInt(partida[2]) == 1 ? "Bot" : "Jugador"
                ,partida[0]
                ,Integer.parseInt(partida[3]) == 1 ? "Bot" : "Jugador"
                ,partida[1])));
    }
    void mostrarElegirPartida(){

        paneMenu.setVisible(false);
        paneElegirPartida.setVisible(true);
    }

    @FXML protected void onButtonVerPartidaClicked(){
        verPartida = true;

        anadirPartidasComboBox(true);

        mostrarElegirPartida();
    }

    @FXML protected void onButtonCancelarElegirPartidaClicked(){
        verPartida = false;
        seguirPartida = false;

        eliminarPartidasComboBox();

        paneElegirPartida.setVisible(false);
        paneMenu.setVisible(true);
    }
    private void eliminarPartidasComboBox() {
        comboBoxElegirPartida.getItems().clear();
    }

    @FXML protected void onButtonAceptarElegirPartidaClicked(){
        if(seguirPartida){
            seguirPartida();
        }else if(verPartida){
            verPartida();
        }
    }

    private void seguirPartida() {
        if(comboBoxTienePartidaElegida()) {

            String[] partidaElegida = getPartidaElegidaComboBox();

            int idPartida = getIdPartida(partidaElegida);

            ponerPartida(idPartida);

            pasarAJuego();
        }
    }
    private boolean comboBoxTienePartidaElegida() {
        return !comboBoxElegirPartida.getSelectionModel().isEmpty();
    }
    private String[] getPartidaElegidaComboBox() {
        return comboBoxElegirPartida.getSelectionModel().getSelectedItem().toString().split(" ");
    }
    private int getIdPartida(String[] partidaElegida) {
        return Integer.parseInt((partidaElegida[1].substring(0, partidaElegida[1].length() - 1)));
    }
    private void ponerPartida(int idPartida) {
        String[] usuariosPartida = mPartidaDAOImpl.getUsuariosPartida(idPartida);

        Usuario usuario1 = usuariosPartida[2].equals("1") && !verPartida ? new Bot(usuariosPartida[0], mUsuarioDAOImpl.getContrasena(usuariosPartida[0])) : new Jugador(usuariosPartida[0], mUsuarioDAOImpl.getContrasena(usuariosPartida[0]));
        Usuario usuario2 = usuariosPartida[3].equals("1") && !verPartida ? new Bot(usuariosPartida[1], mUsuarioDAOImpl.getContrasena(usuariosPartida[1])) : new Jugador(usuariosPartida[1], mUsuarioDAOImpl.getContrasena(usuariosPartida[1]));

        if(verPartida){
            mPartida.setPartidaParaVer(idPartida, usuario1, usuario2);
        }else{
            mPartida.setPartidaParaJugar(idPartida, usuario1, usuario2);
        }
    }



    private void verPartida() {
        setVerPartida(true);
        seguirPartida();
    }

    private void setVerPartida(boolean verPartida){
        this.verPartida = verPartida;
    }

    public static boolean getEnVerPartida(){
        return verPartida;
    }


}



