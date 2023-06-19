package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Partida;
import migrupo.ajedrez.model.Usuario;

public class PartidaDAOImpl implements PartidaDAO{
    //todo: hacer los test de esto
    private PartidaDAOImpl(){

    }
    private final static PartidaDAOImpl mPartidaDAOImpl = new PartidaDAOImpl();
    public static PartidaDAOImpl getInstance(){
        return mPartidaDAOImpl;
    }
    @Override
    public int registrarPartida(Usuario dueno, Usuario contrinctante) {
        return 0;
    }

    @Override
    public Partida getPartida(int codPartida) {
        return null;
    }
}
