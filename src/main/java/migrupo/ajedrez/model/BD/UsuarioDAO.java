package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Bot;
import migrupo.ajedrez.model.Jugador;
import migrupo.ajedrez.model.Usuario;

public interface UsuarioDAO {
    Bot getBot(String nombre);
    Jugador getJugador(String nombre);

}
