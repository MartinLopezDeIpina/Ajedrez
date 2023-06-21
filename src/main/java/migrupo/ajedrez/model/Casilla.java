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
    public int getNumLetra(){
        return letra - 'a';
    }
    public int getNum(){
        return num;
    }
    public Pieza getPieza() {return pieza;}
    public void setPieza(Pieza pieza) {this.pieza = pieza;}

    public boolean estaVacia() {
        return pieza == null;
    }

    public boolean piezaPuedeMoverseACasillaDestino(Casilla destino) {
        return pieza.puedeMoverseA(this, destino);
    }
}
