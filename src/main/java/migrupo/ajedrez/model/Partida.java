package migrupo.ajedrez.model;

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

        int identificador = mPartidaDAO.registrarPartida(usuarioA, usuarioB);

        setPartida(identificador, usuarioA, usuarioB);

        return identificador;
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
