package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tablero {
    private final static Tablero mTablero = new Tablero();
    public static Tablero getInstance(){return mTablero;}

    private Casilla[][] casillas = {
            {new Casilla('a', 1), new Casilla('b', 1), new Casilla('c', 1), new Casilla('d', 1), new Casilla('e', 1), new Casilla('f', 1), new Casilla('g', 1), new Casilla('h', 1)},
            {new Casilla('a', 2), new Casilla('b', 2), new Casilla('c', 2), new Casilla('d', 2), new Casilla('e', 2), new Casilla('f', 2), new Casilla('g', 2), new Casilla('h', 2)},
            {new Casilla('a', 3), new Casilla('b', 3), new Casilla('c', 3), new Casilla('d', 3), new Casilla('e', 3), new Casilla('f', 3), new Casilla('g', 3), new Casilla('h', 3)},
            {new Casilla('a', 4), new Casilla('b', 4), new Casilla('c', 4), new Casilla('d', 4), new Casilla('e', 4), new Casilla('f', 4), new Casilla('g', 4), new Casilla('h', 4)},
            {new Casilla('a', 5), new Casilla('b', 5), new Casilla('c', 5), new Casilla('d', 5), new Casilla('e', 5), new Casilla('f', 5), new Casilla('g', 5), new Casilla('h', 5)},
            {new Casilla('a', 6), new Casilla('b', 6), new Casilla('c', 6), new Casilla('d', 6), new Casilla('e', 6), new Casilla('f', 6), new Casilla('g', 6), new Casilla('h', 6)},
            {new Casilla('a', 7), new Casilla('b', 7), new Casilla('c', 7), new Casilla('d', 7), new Casilla('e', 7), new Casilla('f', 7), new Casilla('g', 7), new Casilla('h', 7)},
            {new Casilla('a', 8), new Casilla('b', 8), new Casilla('c', 8), new Casilla('d', 8), new Casilla('e', 8), new Casilla('f', 8), new Casilla('g', 8), new Casilla('h', 8)}
    };

    private Tablero(){
    }

    public void ponerPosicionesIniciales() {
        ponerPosicionesInicialesPeones();
        ponerPosicionesInicialesTorres();
        ponerPosicionesInicialesCaballos();
        ponerPosicionesInicialesAlfiles();
        ponerPosicionesInicialesReinas();
        ponerPosicionesInicialesReyes();
    }
    private void ponerPosicionesInicialesReyes() {
        casillas[0][4].setPieza(new Rey(Color.BLANCO));
        casillas[7][4].setPieza(new Rey(Color.NEGRO));
    }
    private void ponerPosicionesInicialesReinas() {
        casillas[0][3].setPieza(new Reina(Color.BLANCO));
        casillas[7][3].setPieza(new Reina(Color.NEGRO));
    }
    private void ponerPosicionesInicialesAlfiles() {
        casillas[0][2].setPieza(new Alfil(Color.BLANCO));
        casillas[0][5].setPieza(new Alfil(Color.BLANCO));
        casillas[7][2].setPieza(new Alfil(Color.NEGRO));
        casillas[7][5].setPieza(new Alfil(Color.NEGRO));
    }
    private void ponerPosicionesInicialesCaballos() {
        casillas[0][1].setPieza(new Caballo(Color.BLANCO));
        casillas[0][6].setPieza(new Caballo(Color.BLANCO));
        casillas[7][1].setPieza(new Caballo(Color.NEGRO));
        casillas[7][6].setPieza(new Caballo(Color.NEGRO));
    }
    private void ponerPosicionesInicialesTorres() {
        casillas[0][0].setPieza(new Torre(Color.BLANCO));
        casillas[0][7].setPieza(new Torre(Color.BLANCO));
        casillas[7][0].setPieza(new Torre(Color.NEGRO));
        casillas[7][7].setPieza(new Torre(Color.NEGRO));
    }
    private void ponerPosicionesInicialesPeones() {
        for(int i = 0; i <= 7; i++){
            casillas[1][i].setPieza(new PeonBlanco());
            casillas[6][i].setPieza(new PeonNegro());
        }
    }

    public void hacerMovimiento(Casilla origen, Casilla destino){
        setPiezaEnCasilla(destino, getPiezaEnCasilla(origen));
        setPiezaEnCasilla(origen, new PiezaNula());
    }

    private Pieza getPiezaEnCasilla(Casilla casilla){
        return casillas[casilla.getNum()-1][casilla.getNumLetra()].getPieza();
    }
    private void setPiezaEnCasilla(Casilla casilla, Pieza pieza){
        casillas[casilla.getNum()-1][casilla.getNumLetra()].setPieza(pieza);
    }

    public Color getColorPiezaEnCasilla(Casilla casilla) {
        return getPiezaEnCasilla(casilla).getColor();
    }

    public boolean casillaVacia(Casilla casilla){
        return getPiezaEnCasilla(casilla) instanceof PiezaNula;
    }

    public boolean hayPiezasEntreCasillaOrigenYCasillaDestino(Casilla casillaOrigen, Casilla casillaDestino) {

        List<Casilla> casillasEntreOrigenYDestino = getCasillasEntraOrigenYDestino(casillaOrigen, casillaDestino);

        return casillasEntreOrigenYDestino.stream().anyMatch(Casilla::estaOcupada);
    }
    private List<Casilla> getCasillasEntraOrigenYDestino(Casilla casillaOrigen, Casilla casillaDestino) {

        List<Casilla> casillasEntreOrigenYDestino = new ArrayList<>();

        if(casillaOrigen.estaEnMismaColumna(casillaDestino)){
            casillasEntreOrigenYDestino = getCasillasEnMismaColumna(casillaOrigen, casillaDestino);
        }

        if(casillaOrigen.estaEnMismaFila(casillaDestino)){
            casillasEntreOrigenYDestino = getCasillasEnMismaFila(casillaOrigen, casillaDestino);
        }

        if(casillaOrigen.estaEnMismaDiagonal(casillaDestino)){
            casillasEntreOrigenYDestino = getCasillasEnMismaDiagonal(casillaOrigen, casillaDestino);
        }

        return casillasEntreOrigenYDestino;
    }
    private List<Casilla> getCasillasEnMismaColumna(Casilla casillaOrigen, Casilla casillaDestino) {

        List<Casilla> casillasEntreOrigenYDestino = new ArrayList<>();

        Casilla casillaMasAlta = casillaOrigen.getNum() > casillaDestino.getNum() ? casillaOrigen : casillaDestino;
        Casilla casillaMasBaja = casillaOrigen.getNum() < casillaDestino.getNum() ? casillaOrigen : casillaDestino;

        for(int i = casillaMasAlta.getNum() - 1; i > casillaMasBaja.getNum(); i--){
            casillasEntreOrigenYDestino.add(casillas[i-1][casillaMasAlta.getNumLetra()]);
        }

        return casillasEntreOrigenYDestino;
    }

    private List<Casilla> getCasillasEnMismaFila(Casilla casillaOrigen, Casilla casillaDestino) {

        List<Casilla> casillasEntreOrigenYDestino = new ArrayList<>();

        Casilla casillaMasALaDerecha = casillaOrigen.getNumLetra() > casillaDestino.getNumLetra() ? casillaOrigen : casillaDestino;
        Casilla casillaMasALaIzquierda = casillaOrigen.getNumLetra() < casillaDestino.getNumLetra() ? casillaOrigen : casillaDestino;

        for(int i = casillaMasALaDerecha.getNumLetra() - 1; i > casillaMasALaIzquierda.getNumLetra(); i--){
            casillasEntreOrigenYDestino.add(casillas[casillaMasALaDerecha.getNum()-1][i]);
        }

        return casillasEntreOrigenYDestino;
    }

    private List<Casilla> getCasillasEnMismaDiagonal(Casilla casillaOrigen, Casilla casillaDestino) {

        List<Casilla> casillasEntreOrigenYDestino = new ArrayList<>();

        Casilla casillaMasAlta = casillaOrigen.getNum() > casillaDestino.getNum() ? casillaOrigen : casillaDestino;
        Casilla casillaMasBaja = casillaOrigen.getNum() < casillaDestino.getNum() ? casillaOrigen : casillaDestino;

        int cont = 0;
        for(int i = casillaMasAlta.getNum() - 1; i > casillaMasBaja.getNum(); i--){

            if(casillaMasAlta.getNumLetra() < casillaMasBaja.getNumLetra()){
                cont++;
            }else{
                cont--;
            }

            casillasEntreOrigenYDestino.add(casillas[i-1][casillaMasAlta.getNumLetra()+cont]);
        }

        return casillasEntreOrigenYDestino;
    }

    public boolean reyQuedaEnJaque(Casilla casillaOrigen, Casilla casillaDestino) {

        Pieza piezaDestino = getPiezaEnCasilla(casillaDestino);

        hacerMovimiento(casillaOrigen, casillaDestino);

        boolean reyQuedaEnJaque = algunaPiezaAmenazaAlRey(casillaOrigen.getColorRival());

        deshacerMovimiento(casillaOrigen, casillaDestino, piezaDestino);

        return reyQuedaEnJaque;
    }
    private void deshacerMovimiento(Casilla casillaOrigen, Casilla casillaDestino, Pieza piezaDestino) {
        setPiezaEnCasilla(casillaOrigen, getPiezaEnCasilla(casillaDestino));
        setPiezaEnCasilla(casillaDestino, piezaDestino);
    }
    private boolean algunaPiezaAmenazaAlRey(Color colorRey) {
        Casilla casillaRey = getCasillaRey(colorRey);

        return Arrays.stream(casillas).flatMap(Arrays::stream)
                .filter(casilla -> !casilla.equals(casillaRey) && getColorPiezaEnCasilla(casilla) != colorRey)
                .anyMatch(casilla -> casilla.getPieza().puedeMoverseA(casilla, casillaRey) &&
                        (!hayPiezasEntreCasillaOrigenYCasillaDestino(casilla, casillaRey) || casilla.getPieza() instanceof Caballo));
    }
    private Casilla getCasillaRey(Color colorRey) {

        return Arrays.stream(casillas).flatMap(Arrays::stream)
                .filter(casilla -> getPiezaEnCasilla(casilla) instanceof Rey && getPiezaEnCasilla(casilla).getColor() == colorRey).findFirst().get();

    }

    public void vaciarTablero() {
        Arrays.stream(casillas).flatMap(Arrays::stream).forEach(casilla -> casilla.setPieza(new PiezaNula()));
    }
}
