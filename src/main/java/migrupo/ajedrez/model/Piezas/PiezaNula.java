package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;

public class PiezaNula extends Pieza{
    private Color color;

    public PiezaNula() {
        super(Color.NULO);
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return false;
    }

    @Override
    public String getNombre() {
        return "nulo";
    }
}
