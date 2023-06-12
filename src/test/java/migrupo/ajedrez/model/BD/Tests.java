package migrupo.ajedrez.model.BD;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Tests {
    static ConexionBD mConexionBD = ConexionBD.getInstance();
    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
        mConexionBD.cerrarConexion();
    }
}
