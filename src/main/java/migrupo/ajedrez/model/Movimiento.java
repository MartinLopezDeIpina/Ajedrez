package migrupo.ajedrez.model;

public class Movimiento {
    public Movimiento(Casilla origen, Casilla destino) {
        this.origen = origen;
        this.destino = destino;
    }

    private Casilla origen;
    private Casilla destino;
}

