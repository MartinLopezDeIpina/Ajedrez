package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Bot;
import migrupo.ajedrez.model.Jugador;
import migrupo.ajedrez.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO{
    private UsuarioDAOImpl(){

    }
    private final static UsuarioDAOImpl mUsuarioDAOImpl= new UsuarioDAOImpl();
    public UsuarioDAOImpl getInstance(){
        return mUsuarioDAOImpl;
    }


    @Override
    public Bot getBot(String nombre) {
        return null;
    }

    @Override
    public Jugador getJugador(String nombre) {
        return null;
    }
}
