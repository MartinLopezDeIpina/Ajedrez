package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.Pieza;
import migrupo.ajedrez.model.StateCasilla.Casilla;

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

    public void actualizarPiezas(Pieza[] piezas){
        origen.setPieza(piezas[0]);
        destino.setPieza(piezas[1]);
    }

    public int getNumDestino() {
    	return destino.getNum();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Movimiento){
            Movimiento movimiento = (Movimiento) obj;
            return movimiento.getCasillaOrigen().equals(origen) && movimiento.getCasillaDestino().equals(destino);
        }
        return false;
    }
}

