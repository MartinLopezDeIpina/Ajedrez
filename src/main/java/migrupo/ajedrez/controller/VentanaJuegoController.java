package migrupo.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import migrupo.ajedrez.model.GestorDeMovimientos;
import migrupo.ajedrez.model.StateCasilla.EstadoCasillaNormal;
import migrupo.ajedrez.model.StateCasilla.EstadoCasillaSeleccionado;
import migrupo.ajedrez.model.Tablero;
import migrupo.ajedrez.view.EfectosDAOImpl;
import migrupo.ajedrez.view.PiezasDAOImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaJuegoController implements Initializable {

    private static final float TAMANO_CASILLA = 93.75f;
    private static final float TAMANO_PIEZA = 75f;

    private PiezasDAOImpl mPiezasDAOImpl = PiezasDAOImpl.getInstance();
    private EfectosDAOImpl mEfectosDAOImpl = EfectosDAOImpl.getInstance();
    private GestorDeMovimientos mGestorDeMovimientos = GestorDeMovimientos.getInstance();
    private Tablero mTablero = Tablero.getInstance();

    @FXML protected GridPane gridPaneTablero;
    @FXML protected TextField textFieldNombreA;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        iniciarTablero();
        
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
            if(mouseEvent.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)){
                char letra = (char) (columna + 97);
                mGestorDeMovimientos.casillaSeleccionada(mTablero.getCasilla(letra, fila));
            }

            if (mouseEvent.getButton().equals(javafx.scene.input.MouseButton.SECONDARY)){
                mGestorDeMovimientos.desseleccionar();
            }
        });
    }

    //todo: limpiar esto de alguna manera
    private void configurarSeleccionarCasilla(int fila, int columna, Pane casilla) {
        char letra = (char) (columna + 97);
        mTablero.getCasilla(letra, fila).getEstadoCasilla().addListener(((observable, oldValue, newValue) -> {
            if(newValue instanceof EstadoCasillaNormal){
                casilla.setStyle(getStyleCasilla(fila, columna));
            }
            if(newValue instanceof EstadoCasillaSeleccionado){
                casilla.setStyle("-fx-background-color: #00FF00");
            }
        }));
    }




}
