package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.BD.SimpleFactoryRegistro.FactoryRegistro;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReadWriteLock;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDAOImplTest {

    private UsuarioDAOImpl mUsuarioDAOImpl = UsuarioDAOImpl.getInstance();
    private FactoryRegistro mFactoryRegistro = FactoryRegistro.getInstance();

    @Test
    void getContrasena() {

    }
}