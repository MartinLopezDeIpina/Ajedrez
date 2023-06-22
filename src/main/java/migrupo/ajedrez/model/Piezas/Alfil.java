package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;

public class Alfil extends Pieza{
    public Alfil(Color color) {
        super(color);
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return origen.estaEnMismaDiagonal(destino);
    }
}
