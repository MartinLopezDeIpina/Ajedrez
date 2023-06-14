package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Bot;
import migrupo.ajedrez.model.Jugador;
import migrupo.ajedrez.model.Sesion;
import migrupo.ajedrez.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOImpl implements UsuarioDAO{
    private UsuarioDAOImpl(){

    }
    private final static UsuarioDAOImpl mUsuarioDAOImpl= new UsuarioDAOImpl();
    public static UsuarioDAOImpl getInstance(){
        return mUsuarioDAOImpl;
    }

    private Sesion mSesion = Sesion.getInstance();
    private ConexionBD mConexionBD = ConexionBD.getInstance();

    @Override
    public Bot getBot(String nombre) {
        return null;
    }
    //todo: hacer test de esto
    @Override
    public Jugador getJugador(String nombre) {
        try {

            ResultSet rs = mConexionBD.executeQuery("select contrasena from usuario where nombre = ?", new Object[]{nombre});
            rs.next();
            String contrasena = rs.getString("contrasena");

            return new Jugador(nombre, contrasena);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setJugador(String nombre) {
        mSesion.setJugador(getJugador(nombre));
    }
}
