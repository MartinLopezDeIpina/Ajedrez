package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Movimiento;
import migrupo.ajedrez.model.Partida;
import migrupo.ajedrez.model.Usuario;

public interface PartidaDAO {
    int registrarPartida(Usuario dueno, Usuario contrinctante, boolean[] sonBot);

    void eliminarPartida(int identificador);
}
