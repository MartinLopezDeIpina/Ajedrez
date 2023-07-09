package migrupo.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import migrupo.ajedrez.model.GestorDeMovimientos;
import migrupo.ajedrez.model.Tablero;
import migrupo.ajedrez.view.EfectosDAOImpl;
import migrupo.ajedrez.view.PiezasDAOImpl;
import migrupo.ajedrez.view.TipoEfecto;

import java.net.URL;
import java.util.List;
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
        
        configurarCasilla(casilla, fila, columna);

        actualizarImagenesPiezas(fila, columna, casilla);

        gridPaneTablero.add(casilla, fila, columna);

        configurarClickImagen(casilla, fila, columna);
        configurarClickCasilla(casilla, fila, columna);
    }

    private void configurarCasilla(Pane casilla, int fila, int columna) {
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

    private void configurarClickImagen(Pane casilla, int fila, int columna) {
        List<Node> hijos = casilla.getChildren();
        casilla.getChildren().get(0).setOnMouseClicked(mouseEvent -> {
            configurarClickCasilla(casilla, fila, columna);
        });
    }
    private void configurarClickCasilla(Pane casilla, int fila, int columna) {
        casilla.setOnMouseClicked(mouseEvent -> {
            //casilla.setStyle("-fx-background-color: #00FF00");
            //casilla.getChildren().get(0).setEffect(mEfectosDAOImpl.getEfecto(TipoEfecto.SELECCIONADO));

            char letra = (char) (fila + 97);
            mGestorDeMovimientos.casillaSeleccionada(mTablero.getCasilla(letra, fila));
        });
    }


}
