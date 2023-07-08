package migrupo.ajedrez.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import migrupo.ajedrez.AjedrezApplication;
import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Piezas.Pieza;
import migrupo.ajedrez.model.Tablero;
import migrupo.ajedrez.view.PiezasDAOImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaJuegoController implements Initializable {

    private static final float TAMANO_CASILLA = 93.75f;
    private static final float TAMANO_PIEZA = 75f;

    private PiezasDAOImpl mPiezasDAOImpl = PiezasDAOImpl.getInstance();
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

        if(nombrePieza.equals("nulo")){
            return;
        }
        
        casilla.getChildren().add(crearImagen(nombrePieza));
    }



    private ImageView crearImagen(String nombrePieza) {
        ImageView imagen = new ImageView();
        
        configurarImagen(imagen);

        imagen.setImage(mPiezasDAOImpl.getImagenPieza(nombrePieza));

        return imagen;
    }

    private void configurarImagen(ImageView imagen) {

        imagen.setFitHeight(TAMANO_PIEZA);
        imagen.setFitWidth(TAMANO_PIEZA);

        imagen.layoutXProperty().set( (TAMANO_CASILLA - TAMANO_PIEZA) / 2);
        imagen.layoutYProperty().set( (TAMANO_CASILLA - TAMANO_PIEZA) / 2);

        imagen.setRotate(90);
    }


}
