package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Bot;
import migrupo.ajedrez.model.Jugador;

public interface UsuarioDAO {
    Bot getBot(String nombre);
    Jugador getJugador(String nombre);

    void setJugadorSesion(String nombre);
    String getContrasena(String nombre);
    boolean existeJugador(String nombre);
    void registrarUsuario(String nombre, String contrasena);
    void eliminarUsuario(String nombre);


}
