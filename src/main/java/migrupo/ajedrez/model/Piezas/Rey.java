package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;

public class Rey extends Pieza{
    public Rey(Color color) {
        super(color);
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return seMueveLaterlamente(origen, destino) || seMueveDiagonalmente(origen, destino);
    }
    private boolean seMueveLaterlamente(Casilla origen, Casilla destino) {
        return seMueveHorizontalmente(origen, destino) || seMueveVerticalmente(origen, destino);
    }
    private boolean seMueveHorizontalmente(Casilla origen, Casilla destino) {
        return origen.estaEnMismaFila(destino) && origen.getDistanciaHorizontalAbs(destino) == 1;
    }
    private boolean seMueveVerticalmente(Casilla origen, Casilla destino) {
        return origen.estaEnMismaColumna(destino) && origen.getDistanciaVerticalAbs(destino) == 1;
    }
    private boolean seMueveDiagonalmente(Casilla origen, Casilla destino) {
        return origen.estaEnMismaDiagonal(destino) && origen.getDistanciaDiagonalAbs(destino) == 1;
    }

    @Override
    public String getNombre() {
        return getColor() == Color.BLANCO ? "reyB" : "reyN";
    }

}
