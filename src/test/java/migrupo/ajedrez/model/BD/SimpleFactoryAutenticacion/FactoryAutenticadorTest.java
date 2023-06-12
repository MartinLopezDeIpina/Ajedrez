package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

import migrupo.ajedrez.model.BD.ConexionBD;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class FactoryAutenticadorTest {

    ConexionBD mConexionBD = ConexionBD.getInstance();
    FactoryAutenticador mFactoryAutenticador = FactoryAutenticador.getInstance();

    @BeforeEach
    void setUp(){
        anadirUsuarioPrueba();
    }
    private void anadirUsuarioPrueba(){
        //todo: cuando haya una clase que haga esto reemplazarlo
        mConexionBD.executeUpdate("insert into usuario values ('nombrePrueba', 'contrasenaPrueba')", new Object[0]);
    }
    @Test
    void getAutenticacion() {

        Autenticacion autenticacionCorrecta = mFactoryAutenticador.getAutenticacion("nombrePrueba", "contrasenaPrueba");
        Autenticacion autenticacionContrasenaIncorrecta = mFactoryAutenticador.getAutenticacion("nombrePrueba", "contrasenaIncorrecta");
        Autenticacion autenticacionNombreInexistente = mFactoryAutenticador.getAutenticacion("nombreInexistente", "contrasenaPrueba");

        assertInstanceOf(AutenticacionCorrecto.class, autenticacionCorrecta);
        assertInstanceOf(AutenticacionContrasenaIncorrecta.class, autenticacionContrasenaIncorrecta);
        assertInstanceOf(AutenticacionNombreInexistente.class, autenticacionNombreInexistente);

    }

    @AfterEach
    void tearDown(){
        eliminarUsuariosPrueba();
    }
    private void eliminarUsuariosPrueba(){
        mConexionBD.executeUpdate("delete from usuario where nombre = 'nombrePrueba'", new Object[0]);
    }
}