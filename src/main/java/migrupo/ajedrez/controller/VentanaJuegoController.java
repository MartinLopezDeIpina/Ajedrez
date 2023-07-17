package migrupo.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import migrupo.ajedrez.model.*;
import migrupo.ajedrez.model.StateCasilla.EstadoCasilla;
import migrupo.ajedrez.model.StateCasilla.EstadoCasillaNormal;
import migrupo.ajedrez.model.StateCasilla.EstadoCasillaSeleccionado;
import migrupo.ajedrez.view.EfectosDAOImpl;
import migrupo.ajedrez.view.PiezasDAOImpl;
import migrupo.ajedrez.view.TipoEfecto;
import migrupo.ajedrez.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaJuegoController implements Initializable {

    private static final float TAMANO_CASILLA = 93.75f;
    private static final float TAMANO_PIEZA = 75f;

    private PiezasDAOImpl mPiezasDAOImpl = PiezasDAOImpl.getInstance();
    private EfectosDAOImpl mEfectosDAOImpl = EfectosDAOImpl.getInstance();
    private GestorDeMovimientos mGestorDeMovimientos = GestorDeMovimientos.getInstance();
    private GestorDeTurnos mGestorDeTurnos = GestorDeTurnos.getInstance();
    private Tablero mTablero = Tablero.getInstance();
    private Partida mPartida = Partida.getInstance();

    private static boolean enVerPartida = false;

    @FXML protected GridPane gridPaneTablero;
    @FXML protected TextField textFieldNombreA, textFieldNombreB, textFieldNombreUsuarioActual, textFieldGanador, textFieldRazonVictoria;
    @FXML protected ImageView imageViewUsuarioActual, imageViewGanador;
    @FXML protected AnchorPane paneAcabado;
    @FXML protected Button buttonAtras, buttonFlechaAtras, buttonFlechaAlante;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setEnVerPartida();

        ocultarNecesario();
        
        iniciarTablero();

        iniciarInformacionJugadores();

        iniciarFinalizarPartida();
        
    }

    private void ocultarNecesario() {
        ocultarPaneAcabado();

        ocultarFlechasVerPartida();
    }

    private void ocultarFlechasVerPartida() {
        if(enVerPartida){
            buttonFlechaAlante.setVisible(true);
            buttonFlechaAtras.setVisible(true);
        }else{
            buttonFlechaAlante.setVisible(false);
            buttonFlechaAtras.setVisible(false);
        }
    }

    private void ocultarPaneAcabado() {
        paneAcabado.setVisible(false);
        paneAcabado.setDisable(true);
    }

    private void iniciarTablero() {
        
        ponerCasillas();

    }

    private void ponerCasillas() {
        for(int fila = 0; fila < 8; fila++){
            for(int columna = 0; columna < 8; columna++){
                ponerCasilla(fila, columna);
            }
        }
    }
    private void ponerCasilla(int fila, int columna) {
        Pane casilla = new Pane();
        
        configurarVisualCasilla(casilla, fila, columna);

        gridPaneTablero.add(casilla, fila, columna);

        configurarFuncionalidadCasilla(casilla, fila, columna);
    }

    private void configurarVisualCasilla(Pane casilla, int fila, int columna) {
        configurarParametrosCasilla(casilla, fila, columna);

        actualizarImagenesPiezas(fila, columna, casilla);
    }

    private void configurarParametrosCasilla(Pane casilla, int fila, int columna) {
        casilla.setPrefSize(TAMANO_CASILLA, TAMANO_CASILLA);

        casilla.setStyle(getStyleCasilla(fila, columna));
    }

    private String getStyleCasilla(int fila, int columna) {
        if((fila + columna) % 2 == 0){
            return "-fx-background-color: #FFFFFF";
        }else{
            return "-fx-background-color: #8e8e8e";
        }
    }

    private void actualizarImagenesPiezas(int fila, int columna, Pane casilla) {

        ponerImagenPieza(casilla, mTablero.getPiezaEnCasilla(fila, columna).getValue().getNombre());

        mTablero.getPiezaEnCasilla(fila, columna).addListener(((observable, oldValue, newValue) -> {
            ponerImagenPieza(casilla, newValue.getNombre());
        }));
    }

    private void ponerImagenPieza(Pane casilla, String nombrePieza) {

        casilla.getChildren().clear();

        casilla.getChildren().add(crearImagen(nombrePieza));
    }
    private ImageView crearImagen(String nombrePieza) {
        ImageView imagen = new ImageView();
        
        configurarImagen(imagen);

        if(!nombrePieza.equals("nulo")){
            imagen.setImage(mPiezasDAOImpl.getImagenPieza(nombrePieza));
        }

        return imagen;
    }
    private void configurarImagen(ImageView imagen) {

        imagen.setFitHeight(TAMANO_PIEZA);
        imagen.setFitWidth(TAMANO_PIEZA);

        imagen.layoutXProperty().set( (TAMANO_CASILLA - TAMANO_PIEZA) / 2);
        imagen.layoutYProperty().set( (TAMANO_CASILLA - TAMANO_PIEZA) / 2);

        imagen.setRotate(90);
    }

    private void configurarFuncionalidadCasilla(Pane casilla, int fila, int columna){
        configurarClickImagen(casilla, fila, columna);
        configurarClickCasilla(casilla, fila, columna);

        configurarSeleccionarCasilla(fila, columna, casilla);
    }

    private void configurarClickImagen(Pane casilla, int fila, int columna) {
        casilla.getChildren().get(0).setOnMouseClicked(mouseEvent -> {
            configurarClickCasilla(casilla, fila, columna);
        });
    }
    private void configurarClickCasilla(Pane casilla, int fila, int columna) {
        casilla.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(javafx.scene.input.MouseButton.PRIMARY) && !enVerPartida){
                char letra = (char) (columna + 97);
                mGestorDeMovimientos.casillaSeleccionada(mTablero.getCasilla(letra, fila));
            }

            if (mouseEvent.getButton().equals(javafx.scene.input.MouseButton.SECONDARY) && !enVerPartida) {
                mGestorDeMovimientos.desseleccionar();
            }
        });
    }

    private void configurarSeleccionarCasilla(int fila, int columna, Pane casilla) {
        char letra = (char) (columna + 97);

        mTablero.getCasilla(letra, fila).getEstadoCasilla().addListener(((observable, oldValue, newValue) -> {
            eliminarFiltrosSiDesseleccionadoCasilla(casilla, newValue);
            aplicarFiltrosSiSeleccionadoCasilla(casilla, newValue);
        }));
    }
    private void eliminarFiltrosSiDesseleccionadoCasilla(Pane casilla, EstadoCasilla newValue) {
        if(newValue instanceof EstadoCasillaNormal){
            casilla.setEffect(null);
        }
    }
    private void aplicarFiltrosSiSeleccionadoCasilla(Pane casilla, EstadoCasilla newValue) {
        if(newValue instanceof EstadoCasillaSeleccionado){
            casilla.setEffect(mEfectosDAOImpl.getEfecto(TipoEfecto.SELECCIONADO));
        }
    }

    private void iniciarInformacionJugadores() {
        iniciarJugadores();

        iniciarUsuarioActual();
    }

    private void iniciarJugadores() {
        textFieldNombreA.setText(mGestorDeTurnos.getNombreUsuarioNegro().getValue());
        mGestorDeTurnos.getNombreUsuarioNegro().addListener(((observable, oldValue, newValue) -> {
            textFieldNombreA.setText(newValue);
        }));

        textFieldNombreB.setText(mGestorDeTurnos.getNomberUsuarioBlanco().getValue());
        mGestorDeTurnos.getNomberUsuarioBlanco().addListener(((observable, oldValue, newValue) -> {
            textFieldNombreB.setText(newValue);
        }));
    }
    private void iniciarUsuarioActual() {
        textFieldNombreUsuarioActual.setText(mGestorDeTurnos.getUsuarioActual().getValue().getNombreValue());
        mGestorDeTurnos.getUsuarioActual().addListener(((observable, oldValue, newValue) -> {
            textFieldNombreUsuarioActual.setText(newValue.getNombreValue());
        }));

        imageViewUsuarioActual.setImage(mGestorDeTurnos.getUsuarioActual().getValue().getColor().equals(Color.BLANCO) ? mPiezasDAOImpl.getImagenPieza("reyB") : mPiezasDAOImpl.getImagenPieza("reyN"));
        mGestorDeTurnos.getUsuarioActual().addListener(((observable, oldValue, newValue) -> {
            imageViewUsuarioActual.setImage(newValue.getColor().equals(Color.BLANCO) ? mPiezasDAOImpl.getImagenPieza("reyB") : mPiezasDAOImpl.getImagenPieza("reyN"));
        }));
    }

    private void iniciarFinalizarPartida(){
        mGestorDeTurnos.getAcabado().addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                mostrarMensajeFinPartida();
            }
        }));
    }

    private void mostrarMensajeFinPartida() {
        mostrarPaneAcabado();
        ponerGanador();
    }
    private void mostrarPaneAcabado() {
        paneAcabado.setVisible(true);
        paneAcabado.setDisable(false);
    }
    private void ponerGanador() {
        setImagenGanador();
        setNombreGanador();
        setRazonVictoria();
    }
    private void setImagenGanador() {
        imageViewGanador.setImage(mPartida.getGanador().getColor().equals(Color.BLANCO) ? mPiezasDAOImpl.getImagenPieza("reyB") : mPiezasDAOImpl.getImagenPieza("reyN"));
    }
    private void setNombreGanador() {
        if(mPartida.getRazonVictoria().equals(RazonVictoria.JACKE_MATE.toString())){
            textFieldGanador.setText(String.format("Ganador: %s", mPartida.getGanador().getNombreValue()));
        }else {
            textFieldGanador.setText(String.format("Empate!!"));
        }
    }
    private void setRazonVictoria() {
        textFieldRazonVictoria.setText(mPartida.getRazonVictoria());
    }

    @FXML protected void onButtonAceptarClicked() {
        ocultarVentanaJuego();
        mostrarVentanaMenu();
    }

    private void ocultarVentanaJuego() {
        gridPaneTablero.getScene().getWindow().hide();
    }

    private void mostrarVentanaMenu() {
        ViewFactory.mostrarVentanaMenuPrincipal();
    }

    private void setEnVerPartida() {
        this.enVerPartida = VentanaMenuPrincipalController.getEnVerPartida();;
    }

    @FXML protected void onButtonAtrasClicked(){
        ocultarVentanaJuego();
        mostrarVentanaMenu();
    }

    @FXML protected void onButtonFlechaAlanteClicked(){
        mGestorDeMovimientos.movimientoParaAlante();
    }

    @FXML protected void onButtonFlechaAtrasClicked(){
        mGestorDeMovimientos.movimientoParaAtras();
    }


}
