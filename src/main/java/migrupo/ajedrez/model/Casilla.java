package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.Pieza;

public class Casilla {
    private char letra;
    private int num;

    private Pieza pieza;

    public Casilla(char letra, int num){
        this.letra = letra;
        this.num = num;
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
    public boolean estaOcupada() {
        return !estaVacia();
    }

    public boolean piezaPuedeMoverseACasillaDestino(Casilla destino) {
        return pieza.puedeMoverseA(this, destino);
    }

    public int getDistanciaVertical(Casilla destino) {
        return getNum() - destino.getNum();
    }
    public int getDistanciaVerticalAbs(Casilla destino) {
        return Math.abs(getNum() - destino.getNum());
    }

    public int getDistanciaHorizontalAbs(Casilla destino) {
       return Math.abs(getNumLetra() - destino.getNumLetra());
    }
    public int getDistanciaHorizontal(Casilla destino) {
        return getNumLetra() - destino.getNumLetra();
    }

    public boolean hayPiezaColor(Color color) {
        return !estaVacia() && pieza.esColor(color);
    }

    public boolean estaEnMismaColumna(Casilla destino) {
        return getNumLetra() == destino.getNumLetra();
    }

    public boolean estaEnMismaFila(Casilla destino) {
        return getNum() == destino.getNum();
    }

    public boolean estaEnMismaDiagonal(Casilla destino) {
        return getDistanciaDiagonalAbs(destino) == getDistanciaVerticalAbs(destino);
    }

    public boolean estaEnL(Casilla destino) {
        return (getDistanciaHorizontalAbs(destino) == 2 && getDistanciaVerticalAbs(destino) == 1) ||
                (getDistanciaHorizontalAbs(destino) == 1 && getDistanciaVerticalAbs(destino) == 2);
    }

    public int getDistanciaDiagonalAbs(Casilla destino) {
        return Math.abs(getDistanciaHorizontal(destino));
    }
}
