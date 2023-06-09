package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;

public class PeonBlanco extends Peon {
    public PeonBlanco() {
        super(Color.BLANCO);
    }

    @Override
    protected boolean origenEstaAunaSinBloquear(Casilla origen, Casilla destino) {
        return origen.estaEnMismaColumna(destino) && origen.getDistanciaVertical(destino) == -1 && destino.estaVacia();
    }

    @Override
    protected boolean origenEstaADosDesdeInicio(Casilla origen, Casilla destino) {
        return origen.estaEnMismaColumna(destino) && origen.getNum() == 1 && origen.getDistanciaVertical(destino) == -2;
    }

    @Override
    protected boolean origenEstaAUnaDiagonal(Casilla origen, Casilla destino) {
        return origen.getDistanciaVertical(destino) == -1 && origen.getDistanciaHorizontalAbs(destino) == 1 && destino.hayPiezaColor(Color.NEGRO);
    }

    @Override
    public String getNombre() {
        return "peonB";
    }
}

