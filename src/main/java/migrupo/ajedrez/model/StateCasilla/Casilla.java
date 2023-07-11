package migrupo.ajedrez.model.StateCasilla;

import javafx.beans.property.SimpleObjectProperty;
import migrupo.ajedrez.model.Color;
import migrupo.ajedrez.model.Piezas.Pieza;
import migrupo.ajedrez.model.Piezas.PiezaNula;

public class Casilla implements EstadoCasilla{
    private char letra;
    private int num;

    private SimpleObjectProperty<EstadoCasilla> estadoCasilla;

    private SimpleObjectProperty<Pieza> pieza;

    public Casilla(char letra, int num){
        this.letra = letra;
        this.num = num;
        this.pieza = new SimpleObjectProperty<>(new PiezaNula());

        estadoCasilla = new SimpleObjectProperty<>(new EstadoCasillaNormal(this));
    }

    public int getNumLetra(){
        return letra - 'a';
    }
    public static int getNumLetra(char letra){
        return letra - 'a';
    }
    public int getNum(){
        return num;
    }
    public  SimpleObjectProperty<Pieza> getPieza() {return pieza;}
    public Pieza getPiezaValue() {return pieza.getValue();}
    public void setPieza(Pieza pieza) {this.pieza.set(pieza);}

    public boolean estaVacia() {
        return pieza.getValue() instanceof PiezaNula;
    }
    public boolean estaOcupada() {
        return !estaVacia();
    }

    public boolean piezaPuedeMoverseACasillaDestino(Casilla destino) {
        return pieza.getValue().puedeMoverseA(this, destino);
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
        return !estaVacia() && pieza.getValue().esColor(color);
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

    public Color getColorPiezaCasilla() {
        return pieza.getValue().getColor();
    }

    public String toString() {
        return letra + "" + num;
    }


    public void setEstadoCasilla(EstadoCasilla estadoCasilla) {
        this.estadoCasilla.set(estadoCasilla);
    }

    @Override
    public void seleccionarCasilla() {
        estadoCasilla.getValue().seleccionarCasilla();
    }

    @Override
    public void deseleccionarCasilla() {
        estadoCasilla.getValue().deseleccionarCasilla();
    }

    public SimpleObjectProperty<EstadoCasilla> getEstadoCasilla() {
        return estadoCasilla;
    }
}
