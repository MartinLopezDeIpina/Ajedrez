package migrupo.ajedrez.model;

import javafx.beans.property.SimpleBooleanProperty;
import migrupo.ajedrez.model.BD.PartidaDAOImpl;

public class Partida {
    private final static Partida mPartida = new Partida();
    public static Partida getInstance(){return mPartida;}

    GestorDeMovimientos mGestorDeMovimientos;
    GestorDeTurnos mGestorDeTurnos;
    PartidaDAOImpl mPartidaDAO;

    private int identificador;

    private Usuario usuarioB;
    private Usuario usuarioN;
    private Usuario ganador;

    private RazonVictoria razonVictoria;

    private Partida(){
        mGestorDeMovimientos = GestorDeMovimientos.getInstance();
        mGestorDeTurnos = GestorDeTurnos.getInstance();
        mPartidaDAO = PartidaDAOImpl.getInstance();
    }

    public int iniciarPartidaNueva(Usuario usuarioA, Usuario usuarioB){

        ponerColoresRandom(usuarioA, usuarioB);

        boolean sonBot[] = getSonBot();

        int identificador = mPartidaDAO.registrarPartida(this.usuarioB, this.usuarioN, sonBot);

        setPartidaParaJugar(identificador, this.usuarioB, this.usuarioN);

        return identificador;
    }

    private boolean[] getSonBot() {
        return new boolean[]{this.usuarioB instanceof Bot, this.usuarioN instanceof Bot};
    }

    private void ponerColoresRandom(Usuario usuarioA, Usuario usuarioB) {

        if (Math.random() < 0.25) {

            this.usuarioB = usuarioA;
            this.usuarioN = usuarioB;

        } else {

            this.usuarioB = usuarioB;
            this.usuarioN = usuarioA;

        }
        this.usuarioN.setColor(Color.NEGRO);
        this.usuarioB.setColor(Color.BLANCO);
    }


    public void setPartidaParaJugar(int identificador, Usuario usuarioA, Usuario usuarioB){
        setPartida(identificador, usuarioA, usuarioB);

        mGestorDeMovimientos.setPartidaParaJugar(identificador, this.usuarioB, this.usuarioN);
    }

    public void setPartidaParaVer(int identificador, Usuario usuarioA, Usuario usuarioB){
        setPartida(identificador, usuarioA, usuarioB);

        mGestorDeMovimientos.setPartidaParaVer(identificador, this.usuarioB, this.usuarioN);
    }

    private void setPartida(int identificador, Usuario usuarioA, Usuario usuarioB){
        this.identificador = identificador;

        actualizarColoresJugadores(usuarioA, usuarioB);
    }

    private void actualizarColoresJugadores(Usuario usuarioA, Usuario usuarioB) {
        this.usuarioB = usuarioA;
        this.usuarioN = usuarioB;

        this.usuarioB.setColor(Color.BLANCO);
        this.usuarioN.setColor(Color.NEGRO);
    }


    public void partidaFinalizada(RazonVictoria razon, Usuario ganador){
        razonVictoria = razon;

        this.ganador = ganador;

        mPartidaDAO.finalizarPartida(identificador);
    }

    public Usuario getGanador(){
        return ganador;
    }
    public String getRazonVictoria(){
        return razonVictoria.toString();
    }

    public int getIdentificador() {
        return identificador;
    }
}
