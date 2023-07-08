package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;

public class Caballo extends Pieza{
    public Caballo(Color color) {
        super(color);
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return origen.estaEnL(destino);
    }

    @Override
    public String getNombre() {
        return getColor() == Color.BLANCO ? "caballoB" : "caballoN";
    }
}
