package migrupo.ajedrez.model.BD;

import com.sun.jdi.event.StepEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConexionBDTest {

    ConexionBD conexion = ConexionBD.getInstance();

    @BeforeEach
    void setUp(){
        try{

            establecerConexion();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void establecerConexion() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method metodoEstablecerConexion = ConexionBD.class.getDeclaredMethod("establecerConexion");
        metodoEstablecerConexion.setAccessible(true);
        metodoEstablecerConexion.invoke(conexion);
    }
    @AfterEach
    void tearDown(){
        conexion.cerrarConexion();
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
        conexion.executeUpdate(updatePrueba);
    }
    private String getContrasenaPrueba() throws SQLException {
        String queryPrueba = "select contrasena from usuario where nombre = 'usuarioPrueba'";
        ResultSet rsPrueba = conexion.executeQuery(queryPrueba);
        rsPrueba.next();
        return rsPrueba.getString("contrasena");
    }
    private void vaciarPruebas(){
        String queryVaciar = "delete from usuario where nombre = 'usuarioPrueba'";
        conexion.executeUpdate(queryVaciar);
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
        return (Connection) conField.get(conexion);
    }
    private Field crearConfield() throws NoSuchFieldException {
        Field conField = ConexionBD.class.getDeclaredField("con");
        conField.setAccessible(true);
        return conField;
    }

    @Test
    void cerrarConexion() throws SQLException, NoSuchFieldException, IllegalAccessException {
        conexion.cerrarConexion();
        Connection con = getConnection();
        assertTrue(con.isClosed());
    }
}