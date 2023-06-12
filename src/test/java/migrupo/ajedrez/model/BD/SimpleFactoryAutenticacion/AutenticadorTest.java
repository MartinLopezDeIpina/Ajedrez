package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

import com.sun.prism.shader.DrawEllipse_ImagePattern_AlphaTest_Loader;
import migrupo.ajedrez.model.BD.ConexionBD;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class AutenticadorTest {

    ConexionBD mConexionBD = ConexionBD.getInstance();
    Autenticador mAutenticador = Autenticador.getInstance();

    @BeforeEach
    void setUp(){
        anadirUsuarioPrueba();
    }
    private void anadirUsuarioPrueba(){
        //todo: cuando haya una clase que haga esto reemplazarlo
        mConexionBD.executeUpdate("insert into usuario values ('nombrePrueba', 'contrasenaPrueba')");
    }
    @Test
    void getAutenticacion() {

        Autenticacion autenticacionCorrecta = mAutenticador.getAutenticacion("nombrePrueba", "contrasenaPrueba");
        Autenticacion autenticacionContrasenaIncorrecta = mAutenticador.getAutenticacion("nombrePrueba", "contrasenaIncorrecta");
        Autenticacion autenticacionNombreInexistente = mAutenticador.getAutenticacion("nombreInexistente", "contrasenaPrueba");

        assertInstanceOf(CorrectoAutenticacion.class, autenticacionCorrecta);
        assertInstanceOf(ContrasenaIncorrectaAutenticacion.class, autenticacionContrasenaIncorrecta);
        assertInstanceOf(NombreInexistenteAutenticacion.class, autenticacionNombreInexistente);

    }

    @AfterEach
    void tearDown(){
        eliminarUsuariosPrueba();
    }
    private void eliminarUsuariosPrueba(){
        mConexionBD.executeUpdate("delete from usuario where nombre = 'nombrePrueba'");
    }
}