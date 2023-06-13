package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

import migrupo.ajedrez.model.BD.ConexionBD;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FactoryRegistroTest {

    static FactoryRegistro mFactoryRegistro = FactoryRegistro.getInstance();
    static ConexionBD mConexionBD = ConexionBD.getInstance();

    @BeforeAll
    public static void setUp(){
        mFactoryRegistro.getRegistro("nombrePrueba", "contrasenaPrueba");
    }
    @AfterAll
    public static void tearDown(){
        mConexionBD.executeUpdate("delete from usuario where nombre = 'nombrePrueba' or nombre = 'nombreDePrueba'", new Object[0]);
    }

    @Test
    void testNombreEnUso() {
        Registro registroNombreEnUso = mFactoryRegistro.getRegistro("nombrePrueba", "contrasenaPrueba");
        assertInstanceOf(RegistroNombreEnUso.class, registroNombreEnUso);
    }
    @Test
    void contrasenaLarga(){
        Registro registroContrasenaLarga = mFactoryRegistro.getRegistro("nombre", "unaContrasenaDemasiadoLarga");
        assertInstanceOf(RegistroContrasenaLarga.class, registroContrasenaLarga);
    }
    @Test
    void contrasenaNulo(){
        Registro registroContrasenaNulo = mFactoryRegistro.getRegistro("nombre", "");
        assertInstanceOf(RegistroContrasenaNulo.class, registroContrasenaNulo);
    }
    @Test
    void nombreLargo(){
        Registro registroNombreLargo = mFactoryRegistro.getRegistro("unNombreDemasiadoLargo", "contrasena");
        assertInstanceOf(RegistroNombreLargo.class, registroNombreLargo);
    }
    @Test
    void nombreNulo(){
        Registro registroNombreNulo = mFactoryRegistro.getRegistro("", "contrasena");
        assertInstanceOf(RegistroNombreNulo.class, registroNombreNulo);
    }
    @Test
    void correcto(){
        Registro registroCorrecto = mFactoryRegistro.getRegistro("nombreDePrueba", "contrasenaPrueba");
        assertInstanceOf(RegistroCorrecto.class, registroCorrecto);
    }

}