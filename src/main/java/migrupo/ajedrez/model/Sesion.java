package migrupo.ajedrez.model;

public class Sesion {
    private final static Sesion mSesion = new Sesion();

    private Sesion(){

    }

    public static Sesion getInstance(){
        return mSesion;
    }

    private Jugador jugador;

    public void cerrarSesion(){
        jugador = null;
    }

    public Jugador getJugador() {
        return jugador;
    }
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
