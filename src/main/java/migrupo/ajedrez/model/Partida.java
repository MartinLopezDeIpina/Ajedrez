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

        ponerColores(usuarioA, usuarioB);

        mGestorDeMovimientos.setPartida(identificador, this.usuarioB, this.usuarioN);
    }

    private void ponerColores(Usuario usuarioA, Usuario usuarioB) {

        if (Math.random() < 0.5) {

            this.usuarioB = usuarioA;
            this.usuarioN = usuarioB;


        } else {

            this.usuarioB = usuarioB;
            this.usuarioN = usuarioA;

        }
        this.usuarioN.setColor(Color.NEGRO);
        this.usuarioB.setColor(Color.BLANCO);
    }


}
