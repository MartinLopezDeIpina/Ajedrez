package migrupo.ajedrez.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import migrupo.ajedrez.AjedrezApplication;
import migrupo.ajedrez.model.BD.ConexionBD;

import java.io.IOException;

public class ViewFactory {
    private static void mostrarVentana(String url, String titulo){
        try {

            FXMLLoader loader = new FXMLLoader(AjedrezApplication.class.getResource(url));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setOnCloseRequest(e ->System.exit(0));
            stage.setTitle(titulo);
            stage.show();

            stage.setOnCloseRequest(windowEvent -> ConexionBD.getInstance().cerrarConexion());

        }catch (IOException e){
            System.out.println(String.format("Error al mostrar la ventana %s", titulo));
            e.printStackTrace();
        }
    }

    public static void mostrarVentanaLog(){
        mostrarVentana("fxml/ventanaLog.fxml", "Log");
    }
    public static void mostrarVentanaInicio(){
        mostrarVentana("fxml/ventanaInicio.fxml", "Inicio");
    }
    public static void mostrarVentanaRegistro(){
        mostrarVentana("fxml/ventanaRegistro.fxml", "Registro");
    }
    public static void mostrarVentanaMenuPrincipal(){mostrarVentana("fxml/VentanaMenuPrincipal.fxml", "Menu Principal");}
}
