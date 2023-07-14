package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;

public class Torre extends Pieza{

    private boolean puedeEnrocar;

    public Torre(Color color) {
        super(color);
        puedeEnrocar = true;
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return (origen.estaEnMismaColumna(destino) || origen.estaEnMismaFila(destino)) && !origen.equals(destino);
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
        return getColor() == Color.BLANCO ? "torreB" : "torreN";
    }
}
