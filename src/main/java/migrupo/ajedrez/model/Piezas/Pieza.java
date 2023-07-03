package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;

public abstract class Pieza {
    private Color color;

    public Pieza(Color color){
        this.color = color;
    }

    public Color getColor(){return color;}

    public abstract boolean puedeMoverseA(Casilla origen, Casilla destino);

    public boolean esColor(Color color) {
        return this.color == color;
    }

    public Color getColorRival() {
        if(color == Color.BLANCO){
            return color.NEGRO;
        }else{
            return color.BLANCO;
        }
    }
}
