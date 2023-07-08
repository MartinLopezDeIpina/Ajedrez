package migrupo.ajedrez.view;

import javafx.scene.image.Image;
import migrupo.ajedrez.AjedrezApplication;
import migrupo.ajedrez.model.BD.ConexionBD;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PiezasDAOImpl implements PiezasDAO {

    private static final PiezasDAOImpl mPiezasDAOImpl = new PiezasDAOImpl();

    private ConexionBD mConexionBD;

    private PiezasDAOImpl(){
        mConexionBD = ConexionBD.getInstance();
    }
    public static PiezasDAOImpl getInstance(){
        return mPiezasDAOImpl;
    }

    @Override
    public Image getImagenPieza(String nombrePieza) {
        String pathImagen = getPathImagenPieza(nombrePieza);


        return new Image(AjedrezApplication.class.getResource(pathImagen).toExternalForm());
    }

    private String getPathImagenPieza(String nombrePieza) {
        String query = String.format("SELECT pathImagen FROM imagenesPiezas WHERE tipoPieza = ?");

        try {

            ResultSet rs = mConexionBD.executeQuery(query, new Object[]{nombrePieza});
            rs.next();
            return rs.getString("pathImagen");

        }catch (SQLException e){
            System.out.println(String.format("Error al obtener la imagen de la pieza %s", nombrePieza));
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
