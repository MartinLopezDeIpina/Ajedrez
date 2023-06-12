package migrupo.ajedrez.model;

public class Sesion {
    private Sesion(){

    }

    private final static Sesion mSesion = new Sesion();

    public Sesion getInstance(){
        return mSesion;
    }

    private Jugador jugador;

    public Jugador getJugador() {
        return jugador;
    }
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
