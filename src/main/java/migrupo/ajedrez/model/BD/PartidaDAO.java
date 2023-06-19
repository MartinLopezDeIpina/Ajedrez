package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Partida;
import migrupo.ajedrez.model.Usuario;

public interface PartidaDAO {
    int registrarPartida(Usuario dueno, Usuario contrinctante);

}
