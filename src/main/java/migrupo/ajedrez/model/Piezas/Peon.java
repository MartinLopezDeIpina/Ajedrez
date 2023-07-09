package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;

public abstract class Peon extends Pieza {
    public Peon(Color color) {
        super(color);
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return origenEstaAuna(origen, destino) || origenEstaADosDesdeInicio(origen, destino) || origenEstaAUnaDiagonal(origen, destino);
    }

    protected abstract boolean origenEstaAuna(Casilla origen, Casilla destino);

    protected abstract boolean origenEstaADosDesdeInicio(Casilla origen, Casilla destino);

    protected abstract boolean origenEstaAUnaDiagonal(Casilla origen, Casilla destino);

}
