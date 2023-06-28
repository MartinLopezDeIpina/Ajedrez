package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

import migrupo.ajedrez.model.BD.ConexionBD;
import migrupo.ajedrez.model.BD.SimpleFactoryRegistro.FactoryRegistro;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class FactoryAutenticadorTest {

    static ConexionBD mConexionBD;
    static FactoryAutenticador mFactoryAutenticador;

    @BeforeAll
    static void setUp(){
        iniciarVariables();

        mConexionBD.establecerConexion();

        anadirUsuarioPrueba();
    }
    private static void iniciarVariables() {
        mConexionBD = ConexionBD.getInstance();
        mFactoryAutenticador = FactoryAutenticador.getInstance();
    }

    private static void anadirUsuarioPrueba(){
        FactoryRegistro.getInstance().getRegistro("nombrePrueba", "contrasenaPrueba");
    }
    @AfterAll
    static void tearDown(){
        eliminarUsuariosPrueba();

        mConexionBD.cerrarConexion();
    }
    private static void eliminarUsuariosPrueba(){
        mConexionBD.executeUpdate("delete from usuario where nombre = 'nombrePrueba'", new Object[0]);
    }
    @Test
    void correcto() {
        Autenticacion autenticacionCorrecta = mFactoryAutenticador.generarAutenticador("nombrePrueba", "contrasenaPrueba");
        assertInstanceOf(AutenticacionCorrecto.class, autenticacionCorrecta);
    }
    @Test
    void contrasenaIncorrecta(){
        Autenticacion autenticacionContrasenaIncorrecta = mFactoryAutenticador.generarAutenticador("nombrePrueba", "contrasenaIncorrecta");
        assertInstanceOf(AutenticacionContrasenaIncorrecta.class, autenticacionContrasenaIncorrecta);
    }
    @Test
    void nombreInexistente(){
        Autenticacion autenticacionNombreInexistente = mFactoryAutenticador.generarAutenticador("nombreInexistente", "contrasenaPrueba");
        assertInstanceOf(AutenticacionNombreInexistente.class, autenticacionNombreInexistente);
    }
}