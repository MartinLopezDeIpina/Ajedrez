package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;

public class Torre extends Pieza{
    public Torre(Color color) {
        super(color);
    }

    @Override
    public boolean puedeMoverseA(Casilla origen, Casilla destino) {
        return (origen.estaEnMismaColumna(destino) || origen.estaEnMismaFila(destino)) && !origen.equals(destino);
    }

    @Override
    public String getNombre() {
        return getColor() == Color.BLANCO ? "torreB" : "torreN";
    }
}
