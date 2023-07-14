package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;

public class Rey extends Pieza{

    private boolean puedeEnrocar;

    public Rey(Color color) {
        super(color);
        puedeEnrocar = true;
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return (seMueveLaterlamente(origen, destino) || seMueveDiagonalmente(origen, destino)) && !origen.equals(destino);
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

    public boolean puedeEnrocar() {
        return puedeEnrocar;
    }
    public void seHaMovido() {
        puedeEnrocar = false;
    }
    public void setPuedeEnrocar(boolean puedeEnrocar) {
        this.puedeEnrocar = puedeEnrocar;
    }

    @Override
    public String getNombre() {
        return getColor() == Color.BLANCO ? "reyB" : "reyN";
    }

}
