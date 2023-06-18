package migrupo.ajedrez.model;

public class Partida {
    private Partida(){

    }
    private final static Partida mPartida = new Partida();
    public static Partida getInstance(){return mPartida;}
    private int identificador;

    public void setPartida(int identificador){
        this.identificador = identificador;
    }
}
