package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

import migrupo.ajedrez.model.BD.ConexionBD;
import migrupo.ajedrez.model.BD.UsuarioDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FactoryRegistro {

    private FactoryRegistro(){

    }
    private final static FactoryRegistro mFactoryRegistro = new FactoryRegistro();
    public static FactoryRegistro getInstance(){
        return mFactoryRegistro;
    }

    private final int MAX_CARACTERES = 20;

    UsuarioDAOImpl mUsuarioDAOImpl = UsuarioDAOImpl.getInstance();

    public Registro getRegistro(String nombre, String contrasena){

        if(nombre.length() > MAX_CARACTERES) return new RegistroNombreLargo();

        if(contrasena.length() > MAX_CARACTERES) return new RegistroContrasenaLarga();

        if(nombre.isEmpty()) return new RegistroNombreNulo();

        if(contrasena.isEmpty()) return new RegistroContrasenaNulo();

        if(mUsuarioDAOImpl.existeJugador(nombre)){return new RegistroNombreEnUso();}

        mUsuarioDAOImpl.registrarUsuario(nombre, contrasena);

        return new RegistroCorrecto();
    }
}
