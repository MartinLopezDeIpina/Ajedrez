package migrupo.ajedrez.model;

public class Movimiento {
    private Casilla origen;
    private Casilla destino;

    public Movimiento(Casilla origen, Casilla destino) {
        this.origen = origen;
        this.destino = destino;
    }

    public Casilla getCasillaOrigen() {
        return origen;
    }
    public Casilla getCasillaDestino() {
        return destino;
    }

    public boolean piezaPuedeMoverseACasillaDestino(){
        return origen.piezaPuedeMoverseACasillaDestino(destino);
    }
}

