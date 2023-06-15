package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

import migrupo.ajedrez.model.BD.ConexionBD;
import migrupo.ajedrez.model.BD.SimpleFactoryRegistro.FactoryRegistro;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class FactoryAutenticadorTest {

    static ConexionBD mConexionBD = ConexionBD.getInstance();
    static FactoryAutenticador mFactoryAutenticador = FactoryAutenticador.getInstance();

    @BeforeAll
    static void setUp(){
        anadirUsuarioPrueba();
    }
    @AfterAll
    static void tearDown(){
        eliminarUsuariosPrueba();
    }
    private static void eliminarUsuariosPrueba(){
        mConexionBD.executeUpdate("delete from usuario where nombre = 'nombrePrueba'", new Object[0]);
    }
    private static void anadirUsuarioPrueba(){
        FactoryRegistro.getInstance().getRegistro("nombrePrueba", "contrasenaPrueba");
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