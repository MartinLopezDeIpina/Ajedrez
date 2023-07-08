package migrupo.ajedrez.view;

import javafx.scene.image.Image;
import migrupo.ajedrez.AjedrezApplication;
import migrupo.ajedrez.model.BD.ConexionBD;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.annotations.Ignore;


import static org.junit.jupiter.api.Assertions.*;

class PiezasDAOImplTest {


    static PiezasDAOImpl mPiezasDAOImpl;
    static ConexionBD mConexionBD;

    @BeforeAll
    static void setUp() {
        mConexionBD = ConexionBD.getInstance();
        mPiezasDAOImpl = PiezasDAOImpl.getInstance();

        mConexionBD.establecerConexion();
    }
    @AfterAll
    static void tearDown() {
        mConexionBD.cerrarConexion();
    }

    @Ignore
    void getImagenPieza() {
        Image imagen = mPiezasDAOImpl.getImagenPieza("peonB");

        assertEquals(new Image(AjedrezApplication.class.getResource("/imagenes/piezas/peonB.png").toExternalForm()), imagen);
    }
}