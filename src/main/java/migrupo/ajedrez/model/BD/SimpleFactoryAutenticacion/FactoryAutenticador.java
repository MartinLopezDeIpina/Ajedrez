package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

import migrupo.ajedrez.model.BD.ConexionBD;
import migrupo.ajedrez.model.BD.UsuarioDAOImpl;
import migrupo.ajedrez.model.Sesion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FactoryAutenticador {
    private FactoryAutenticador(){

    }
    private static final FactoryAutenticador mAutenticador = new FactoryAutenticador();
    public static FactoryAutenticador getInstance(){
        return mAutenticador;
    }

    ConexionBD conexionBD = ConexionBD.getInstance();
    UsuarioDAOImpl mUsuarioDAOImpl = UsuarioDAOImpl.getInstance();
    public Autenticacion getAutenticacion(String nombre, String contrasena){

        try {

            ResultSet rs = getResultSet(nombre);

            return generarAutenticador(rs, contrasena);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    private ResultSet getResultSet(String nombre) throws SQLException {
        return conexionBD.executeQuery("select contrasena from usuario where nombre = ?", new Object[]{nombre});
    }

    private Autenticacion generarAutenticador(ResultSet rs, String contrasena) throws SQLException {

        if(!rs.next()){
            return new AutenticacionNombreInexistente();
        }

        if(rs.getString("contrasena").equals(contrasena)){
            //todo: hacer test de esto

            mUsuarioDAOImpl.setJugador(rs.getString("nombre"));

            return new AutenticacionCorrecto();
        }

        return new AutenticacionContrasenaIncorrecta();
    }

}
