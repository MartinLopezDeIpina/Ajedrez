package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Jugador;
import migrupo.ajedrez.model.Sesion;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDAOImplTest {

    static UsuarioDAOImpl mUsuarioDAOImpl = UsuarioDAOImpl.getInstance();
    static Sesion mSesion = Sesion.getInstance();

    @BeforeAll
    static void setUp(){
        mUsuarioDAOImpl.registrarUsuario("nombrePrueba", "contrasena");
    }
    @AfterAll
    static void tearDown(){
        mUsuarioDAOImpl.eliminarUsuario("nombrePrueba");
    }

    @Test
    void getContrasena() {
        assertEquals("contrasena", mUsuarioDAOImpl.getContrasena("nombrePrueba"));
    }

    @Test
    void existeJugador() {
        assertEquals(true, mUsuarioDAOImpl.existeJugador("nombrePrueba"));
        assertEquals(false, mUsuarioDAOImpl.existeJugador("nombreDePrueba"));
    }

    @Test
    void registrarUsuario() {
        assertEquals(true, mUsuarioDAOImpl.existeJugador("nombrePrueba"));
    }

    @Test
    void setJugadorSesion() {
        mUsuarioDAOImpl.setJugadorSesion("usuarioPrueba");
        Jugador jugador = mSesion.getJugador();

        assertEquals("usuarioPrueba", jugador.getNombreValue());
    }

    @Test
    void eliminarUsuario() {
        mUsuarioDAOImpl.registrarUsuario("nombrePruebas", "contrasena");
        mUsuarioDAOImpl.eliminarUsuario("nombrePruebas");
        assertEquals(false, mUsuarioDAOImpl.existeJugador("nombrePruebas"));
    }
}