package migrupo.ajedrez.model;

public class Partida {
    private Partida(){

    }
    private final static Partida mPartida = new Partida();
    public static Partida getInstance(){return mPartida;}

    GestorDeMovimientos mGestorDeMovimientos = GestorDeMovimientos.getInstance();

    private int identificador;
    private Usuario usuarioA;
    private Usuario usuarioB;

    public void setPartida(int identificador, Usuario usuarioA, Usuario usuarioB){
        this.identificador = identificador;
        this.usuarioA = usuarioA;
        this.usuarioB = usuarioB;

        mGestorDeMovimientos.setPartida(identificador);
    }


}
