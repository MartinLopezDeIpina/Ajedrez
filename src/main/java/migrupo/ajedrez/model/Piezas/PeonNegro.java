package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;

public class PeonNegro extends Peon{
    public PeonNegro() {
        super(Color.NEGRO);
    }

    @Override
    protected boolean origenEstaAuna(Casilla origen, Casilla destino) {
        return origen.estaEnMismaColumna(destino) && origen.getDistanciaVertical(destino) == 1;
    }

    @Override
    protected boolean origenEstaADosDesdeInicio(Casilla origen, Casilla destino) {
        return origen.estaEnMismaColumna(destino) && origen.getNum() == 7 && origen.getDistanciaVertical(destino) == 2;
    }

    @Override
    protected boolean origenEstaAUnaDiagonal(Casilla origen, Casilla destino) {
        return origen.getDistanciaVertical(destino) == 1 && origen.getDistanciaHorizontalAbs(destino) == 1 && destino.hayPiezaColor(Color.BLANCO);
    }

    @Override
    public String getNombre() {
        return "peonN";
    }
}
