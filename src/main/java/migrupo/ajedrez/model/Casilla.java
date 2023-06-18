package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.Pieza;

public class Casilla {
    public Casilla(char letra, int num){
        this.letra = letra;
        this.num = num;
    }
    private char letra;
    private int num;

    private Pieza pieza;

    public char getLetra(){
        return letra;
    }
    public int getNum(){
        return num;
    }
    public Pieza getPieza() {return pieza;}
}
