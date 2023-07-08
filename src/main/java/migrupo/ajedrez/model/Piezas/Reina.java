package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;

public class Reina extends Pieza{
    public Reina(Color color) {
        super(color);
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return origen.estaEnMismaColumna(destino) || origen.estaEnMismaFila(destino) || origen.estaEnMismaDiagonal(destino);
    }

    @Override
    public String getNombre() {
        return getColor() == Color.BLANCO ? "reinaB" : "reinaN";
    }
}
