package migrupo.ajedrez.model.BD;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracionBDTest {

    static ConfiguracionBD conifg;

    @BeforeAll
    static void setUp() {
        conifg = ConfiguracionBD.getInstance();
    }

    @Test
    void getUrl() {
        String url = conifg.getUrl();
        assertEquals("jdbc:mysql://localhost:3306/ajedrez", url);
    }

    @Test
    void getUsuario() {
        String usuario = conifg.getUsuario();
        assertEquals("pepe", usuario);
    }

    @Test
    void getContrasena() {
        String contrasena = conifg.getContrasena();
        assertEquals("123", contrasena);
    }
}