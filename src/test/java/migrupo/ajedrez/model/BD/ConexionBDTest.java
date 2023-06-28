package migrupo.ajedrez.model.BD;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConexionBDTest {

    static ConexionBD mConexion;

    @BeforeAll
    static void setUpAll(){
        mConexion = ConexionBD.getInstance();

        mConexion.establecerConexion();
    }

    @AfterAll
    static void tearDownAll(){
        mConexion.cerrarConexion();
    }

    @Test
    void executeQueryandUpdateQuery()  {
        try {

            insertarValoresPrueba();

            String contrasena = getContrasenaPrueba();

            vaciarPruebas();

            assertEquals("contrasenaPrueba", contrasena);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void insertarValoresPrueba(){
        String updatePrueba = "insert into usuario values ('usuarioPrueba', 'contrasenaPrueba')";
        mConexion.executeUpdate(updatePrueba, new Object[0]);
    }
    private String getContrasenaPrueba() throws SQLException {
        String queryPrueba = "select contrasena from usuario where nombre = 'usuarioPrueba'";
        ResultSet rsPrueba = mConexion.executeQuery(queryPrueba, new Object[0]);
        rsPrueba.next();
        return rsPrueba.getString("contrasena");
    }
    private void vaciarPruebas(){
        String queryVaciar = "delete from usuario where nombre = 'usuarioPrueba'";
        mConexion.executeUpdate(queryVaciar, new Object[0]);
    }

    @Test
    void establecerConexionTest(){
        try{

            Connection con = getConnection();
            assertNotNull(con);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private Connection getConnection() throws IllegalAccessException, NoSuchFieldException {
        Field conField = crearConfield();
        return (Connection) conField.get(mConexion);
    }
    private Field crearConfield() throws NoSuchFieldException {
        Field conField = ConexionBD.class.getDeclaredField("con");
        conField.setAccessible(true);
        return conField;
    }

    @Test
    void cerrarConexion() throws SQLException, NoSuchFieldException, IllegalAccessException {
        mConexion.cerrarConexion();
        Connection con = getConnection();
        assertTrue(con.isClosed());

        mConexion.establecerConexion();
    }
}