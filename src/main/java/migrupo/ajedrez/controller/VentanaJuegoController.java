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

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaJuegoController implements Initializable {

    private static final float TAMANO_CASILLA = 93.75f;
    private static final float TAMANO_PIEZA = 75f;

    @FXML protected GridPane gridPaneTablero;
    @FXML protected TextField textFieldNombreA;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarTablero();

        textFieldNombreA.setText("Hola");
    }

    private void iniciarTablero() {
        ponerCasillas();


    }
    private void ponerCasillas() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                ponerCasilla(i, j);
            }
        }
    }
    private void ponerCasilla(int i, int j) {
        Pane casilla = new Pane();
        casilla.setPrefSize(TAMANO_CASILLA, TAMANO_CASILLA);

        casilla.setStyle(getStyleCasilla(i, j));


        casilla.getChildren().add(crearImagen());


        gridPaneTablero.add(casilla, i, j);
    }
    private String getStyleCasilla(int i, int j) {
        if((i + j) % 2 == 0){
            return "-fx-background-color: #FFFFFF";
        }else{
            return "-fx-background-color: #8e8e8e";
        }
    }
    private ImageView crearImagen() {
        ImageView imagen = new ImageView();

        imagen.setFitHeight(TAMANO_PIEZA);
        imagen.setFitWidth(TAMANO_PIEZA);

        imagen.setImage(new Image(AjedrezApplication.class.getResource("imagenes/piezas/peonN.png").toExternalForm()));

        imagen.layoutXProperty().set( (TAMANO_CASILLA - TAMANO_PIEZA) / 2);
        imagen.layoutYProperty().set( (TAMANO_CASILLA - TAMANO_PIEZA) / 2);

        return imagen;
    }
}
