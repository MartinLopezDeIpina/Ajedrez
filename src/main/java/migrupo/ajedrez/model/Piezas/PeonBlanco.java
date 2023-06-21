package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;

public class PeonBlanco extends Peon {
    public PeonBlanco() {
        super(Color.BLANCO);
    }

    @Override
    protected boolean origenEstaAuna(Casilla origen, Casilla destino) {
        return false;
    }

    @Override
    protected boolean origenEstaADosDesdeInicio(Casilla origen, Casilla destino) {
        return false;
    }

    @Override
    protected boolean origenEstaAUnaDiagonal(Casilla origen, Casilla destino) {
        return false;
    }
}

