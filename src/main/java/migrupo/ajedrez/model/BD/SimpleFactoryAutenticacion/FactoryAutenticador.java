package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

import migrupo.ajedrez.model.BD.ConexionBD;
import migrupo.ajedrez.model.BD.UsuarioDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FactoryAutenticador {
    //todo: hacer que dependa del dao en vez de la conexión
    private FactoryAutenticador(){

    }
    private static final FactoryAutenticador mAutenticador = new FactoryAutenticador();
    public static FactoryAutenticador getInstance(){
        return mAutenticador;
    }

    UsuarioDAOImpl mUsuarioDAOImpl = UsuarioDAOImpl.getInstance();

    public Autenticacion generarAutenticador(String nombre, String contrasena){

        if(!mUsuarioDAOImpl.existeJugador(nombre)){
            return new AutenticacionNombreInexistente();
        }

        if(contrasenaCorrecta(nombre, contrasena)){

            mUsuarioDAOImpl.setJugadorSesion(nombre);

            return new AutenticacionCorrecto();
        }

        return new AutenticacionContrasenaIncorrecta();
    }
    private boolean contrasenaCorrecta(String nombre, String contrasena){
        return mUsuarioDAOImpl.getContrasena(nombre).equals(contrasena);
    }

}
