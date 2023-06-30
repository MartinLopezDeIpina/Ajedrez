package migrupo.ajedrez.model;

public class Partida {
    private final static Partida mPartida = new Partida();
    public static Partida getInstance(){return mPartida;}

    GestorDeMovimientos mGestorDeMovimientos;

    private int identificador;

    //todo: cuando se inicie la partida que se decida que usuario es negro y blanco
    private Usuario usuarioB;
    private Usuario usuarioN;

    private Partida(){
        mGestorDeMovimientos = GestorDeMovimientos.getInstance();
    }

    public void setPartida(int identificador, Usuario usuarioA, Usuario usuarioB){
        this.identificador = identificador;
        this.usuarioB = usuarioA;
        this.usuarioN = usuarioB;

        mGestorDeMovimientos.setPartida(identificador);
    }


}
