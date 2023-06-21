package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;

public class PeonNegro extends Peon{
    public PeonNegro() {
        super(Color.NEGRO);
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
