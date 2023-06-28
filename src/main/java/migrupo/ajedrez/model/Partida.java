package migrupo.ajedrez.model;

public class Partida {
    private final static Partida mPartida = new Partida();
    public static Partida getInstance(){return mPartida;}

    GestorDeMovimientos mGestorDeMovimientos;

    private int identificador;
    private Usuario usuarioA;
    private Usuario usuarioB;

    private Partida(){
        mGestorDeMovimientos = GestorDeMovimientos.getInstance();
    }

    public void setPartida(int identificador, Usuario usuarioA, Usuario usuarioB){
        this.identificador = identificador;
        this.usuarioA = usuarioA;
        this.usuarioB = usuarioB;

        mGestorDeMovimientos.setPartida(identificador);
    }


}
