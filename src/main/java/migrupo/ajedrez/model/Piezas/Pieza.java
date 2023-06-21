package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;

public abstract class Pieza {
    public Pieza(Color color){
        this.color = color;
    }

    private Color color;

    public Color getColor(){return color;}

    public abstract boolean puedeMoverseA(Casilla origen, Casilla destino);
}
