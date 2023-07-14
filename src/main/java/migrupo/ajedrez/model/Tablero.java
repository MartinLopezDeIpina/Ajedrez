package migrupo.ajedrez.model;

import javafx.beans.property.SimpleObjectProperty;
import migrupo.ajedrez.model.Piezas.*;
import migrupo.ajedrez.model.StateCasilla.Casilla;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tablero {
    private final static Tablero mTablero = new Tablero();
    public static Tablero getInstance(){return mTablero;}

    private Casilla[][] casillas = {
            {new Casilla('a', 0), new Casilla('b', 0), new Casilla('c', 0), new Casilla('d', 0), new Casilla('e', 0), new Casilla('f', 0), new Casilla('g', 0), new Casilla('h', 0)},
            {new Casilla('a', 1), new Casilla('b', 1), new Casilla('c', 1), new Casilla('d', 1), new Casilla('e', 1), new Casilla('f', 1), new Casilla('g', 1), new Casilla('h', 1)},
            {new Casilla('a', 2), new Casilla('b', 2), new Casilla('c', 2), new Casilla('d', 2), new Casilla('e', 2), new Casilla('f', 2), new Casilla('g', 2), new Casilla('h', 2)},
            {new Casilla('a', 3), new Casilla('b', 3), new Casilla('c', 3), new Casilla('d', 3), new Casilla('e', 3), new Casilla('f', 3), new Casilla('g', 3), new Casilla('h', 3)},
            {new Casilla('a', 4), new Casilla('b', 4), new Casilla('c', 4), new Casilla('d', 4), new Casilla('e', 4), new Casilla('f', 4), new Casilla('g', 4), new Casilla('h', 4)},
            {new Casilla('a', 5), new Casilla('b', 5), new Casilla('c', 5), new Casilla('d', 5), new Casilla('e', 5), new Casilla('f', 5), new Casilla('g', 5), new Casilla('h', 5)},
            {new Casilla('a', 6), new Casilla('b', 6), new Casilla('c', 6), new Casilla('d', 6), new Casilla('e', 6), new Casilla('f', 6), new Casilla('g', 6), new Casilla('h', 6)},
            {new Casilla('a', 7), new Casilla('b', 7), new Casilla('c', 7), new Casilla('d', 7), new Casilla('e', 7), new Casilla('f', 7), new Casilla('g', 7), new Casilla('h', 7)}
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
        Pieza piezaOrigen = getPiezaEnCasillaValue(origen);

        cancelarEnroques(piezaOrigen);

        setPiezaEnCasilla(destino, piezaOrigen);
        setPiezaEnCasilla(origen, new PiezaNula());
    }

    private void cancelarEnroques(Pieza piezaOrigen) {
        if(piezaOrigen instanceof Rey){
            ((Rey) piezaOrigen).seHaMovido();
        }
        if(piezaOrigen instanceof Torre){
            ((Torre) piezaOrigen).seHaMovido();
        }
    }

    private Pieza getPiezaEnCasillaValue(Casilla casilla){
        return casillas[casilla.getNum()][casilla.getNumLetra()].getPiezaValue();
    }
    public SimpleObjectProperty<Pieza> getPiezaEnCasilla(int fila, int columna) {
        return casillas[fila][columna].getPieza();
    }
    private void setPiezaEnCasilla(Casilla casilla, Pieza pieza){
        casillas[casilla.getNum()][casilla.getNumLetra()].setPieza(pieza);
    }

    public Color getColorPiezaEnCasilla(Casilla casilla) {
        return getPiezaEnCasillaValue(casilla).getColor();
    }

    public boolean casillaVacia(Casilla casilla){
        return getPiezaEnCasillaValue(casilla) instanceof PiezaNula;
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
            casillasEntreOrigenYDestino.add(casillas[i][casillaMasAlta.getNumLetra()]);
        }

        return casillasEntreOrigenYDestino;
    }

    private List<Casilla> getCasillasEnMismaFila(Casilla casillaOrigen, Casilla casillaDestino) {

        List<Casilla> casillasEntreOrigenYDestino = new ArrayList<>();

        Casilla casillaMasALaDerecha = casillaOrigen.getNumLetra() > casillaDestino.getNumLetra() ? casillaOrigen : casillaDestino;
        Casilla casillaMasALaIzquierda = casillaOrigen.getNumLetra() < casillaDestino.getNumLetra() ? casillaOrigen : casillaDestino;

        for(int i = casillaMasALaDerecha.getNumLetra() - 1; i > casillaMasALaIzquierda.getNumLetra(); i--){
            casillasEntreOrigenYDestino.add(casillas[casillaMasALaDerecha.getNum()][i]);
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

            casillasEntreOrigenYDestino.add(casillas[i][casillaMasAlta.getNumLetra()+cont]);
        }

        return casillasEntreOrigenYDestino;
    }

    public boolean reyQuedaEnJaque(Casilla casillaOrigen, Casilla casillaDestino) {

        Pieza piezaDestino = getPiezaEnCasillaValue(casillaDestino);
        Pieza piezaOrigen = getPiezaEnCasillaValue(casillaOrigen);
        Color colorRey = casillaOrigen.getColorPiezaCasilla();

        boolean puedeEnrocar = guardarPuedeEnrocar(piezaOrigen);

        hacerMovimiento(casillaOrigen, casillaDestino);

        boolean reyQuedaEnJaque = algunaPiezaAmenazaAlRey(colorRey);

        deshacerMovimiento(casillaOrigen, casillaDestino, piezaOrigen, piezaDestino);

        setPuedeEnrocarDeVuelta(piezaOrigen, puedeEnrocar);

        return reyQuedaEnJaque;
    }
    private void deshacerMovimiento(Casilla casillaOrigen, Casilla casillaDestino, Pieza piezaOrigen, Pieza piezaDestino) {
        setPiezaEnCasilla(casillaOrigen, piezaOrigen);
        setPiezaEnCasilla(casillaDestino, piezaDestino);
    }
    private boolean guardarPuedeEnrocar(Pieza piezaOrigen) {
        boolean puedeEnrocar = false;
        if (piezaOrigen instanceof Rey) {
            puedeEnrocar = ((Rey) piezaOrigen).puedeEnrocar();
        }
        if(piezaOrigen instanceof Torre){
            puedeEnrocar = ((Torre) piezaOrigen).puedeEnrocar();
        }
        return puedeEnrocar;
    }
    private void setPuedeEnrocarDeVuelta(Pieza piezaOrigen, boolean puedeEnrocar) {
        if(piezaOrigen instanceof Rey){
            ((Rey) piezaOrigen).setPuedeEnrocar(puedeEnrocar);
        }
        if(piezaOrigen instanceof Torre){
            ((Torre) piezaOrigen).setPuedeEnrocar(puedeEnrocar);
        }
    }

    public boolean algunaPiezaAmenazaAlRey(Color colorRey) {
        Casilla casillaRey = getCasillaRey(colorRey);

        return Arrays.stream(casillas).flatMap(Arrays::stream)
                .filter(casilla -> !casilla.equals(casillaRey) && getColorPiezaEnCasilla(casilla) != colorRey)
                .anyMatch(casilla -> casilla.getPiezaValue().puedeMoverseA(casilla, casillaRey) &&
                        (!hayPiezasEntreCasillaOrigenYCasillaDestino(casilla, casillaRey) || casilla.getPiezaValue() instanceof Caballo));
    }
    private Casilla getCasillaRey(Color colorRey) {

        return Arrays.stream(casillas).flatMap(Arrays::stream)
                .filter(casilla -> getPiezaEnCasillaValue(casilla) instanceof Rey && getPiezaEnCasillaValue(casilla).getColor() == colorRey).findFirst().get();

    }

    public void reiniciarTablero(){
        vaciarTablero();
        ponerPosicionesIniciales();
    }

    public void vaciarTablero() {
        Arrays.stream(casillas).flatMap(Arrays::stream).forEach(casilla -> casilla.setPieza(new PiezaNula()));
    }

    public Casilla getCasilla(char letra, int num) {
        return casillas[num][Casilla.getNumLetra(letra)];
    }


    public Pieza[] getPiezas(Casilla casillaOrigen, Casilla casillaDestino) {

            Pieza[] piezas = new Pieza[2];

            piezas[0] = getPiezaEnCasillaValue(casillaOrigen);
            piezas[1] = getPiezaEnCasillaValue(casillaDestino);

            return piezas;
    }

    public void coronar(Casilla casillaOrigen, Casilla casillaDestino) {
        setPiezaEnCasilla(casillaDestino, new Reina(casillaOrigen.getColorPiezaCasilla()));
        setPiezaEnCasilla(casillaOrigen, new PiezaNula());
    }


    public List<Casilla[]> getMovimientosPosibles(Color color) {
        return Arrays.stream(casillas)
                .flatMap(Arrays::stream)
                .filter(casilla -> casilla.getColorPiezaCasilla() == color)
                .flatMap(casilla -> Arrays.stream(casillas)
                        .flatMap(Arrays::stream)
                        .filter(casillaDestino -> casilla.getPiezaValue().puedeMoverseA(casilla, casillaDestino))
                        .map(casillaDestino -> new Casilla[]{casilla, casillaDestino})
                )
                .toList();
    }



    public boolean materialInsuficiente() {
        return materialInsuficiente(Color.BLANCO) && materialInsuficiente(Color.NEGRO);
    }

    private boolean materialInsuficiente(Color color) {
        List<Pieza> piezas = getPiezasEquipoSinRey(color);

        boolean piezaBuena = equipoTienePiezaBuena(piezas);
        boolean hayUnMinimoDePiezas = hayUnMinimoDePiezas(piezas);
        boolean dosCaballos = sonDosCaballos(piezas);

        return !(equipoTienePiezaBuena(piezas) || hayUnMinimoDePiezas(piezas)) || sonDosCaballos(piezas);
    }
    private List<Pieza> getPiezasEquipoSinRey(Color color){
        return Arrays.stream(casillas).flatMap(Arrays::stream)
                .map(Casilla::getPiezaValue)
                .filter(pieza -> !(pieza instanceof Rey || pieza instanceof PiezaNula))
                .filter(pieza -> pieza.getColor() == color)
                .toList();
    }
    private boolean equipoTienePiezaBuena(List<Pieza> piezas){
        return piezas.parallelStream().anyMatch(
                pieza -> pieza instanceof PeonBlanco ||
                         pieza instanceof PeonNegro ||
                         pieza instanceof Torre ||
                         pieza instanceof Reina
        );
    }
    private boolean hayUnMinimoDePiezas(List<Pieza> piezas){
        return piezas.size() >= 2;
    }
    private boolean sonDosCaballos(List<Pieza> piezas){
        return piezas.stream().filter(pieza -> pieza instanceof Caballo).count() == 2 && piezas.size() == 2;
    }


    public boolean esEnroqueValido(Casilla casillaOrigen, Casilla casillaDestino) {
        Pieza piezaOrigen = getPiezaEnCasillaValue(casillaOrigen);
        Pieza piezaDestino = getPiezaEnCasillaValue(casillaDestino);

        if(piezaOrigen instanceof Rey && piezaDestino instanceof Torre){
            return ((Rey) piezaOrigen).puedeEnrocar() && ((Torre) piezaDestino).puedeEnrocar();
        }
        return false;
    }
    public void enrocar(Casilla casillaOrigen, Casilla casillaDestino) {
        Pieza piezaOrigen = getPiezaEnCasillaValue(casillaOrigen);
        Pieza piezaDestino = getPiezaEnCasillaValue(casillaDestino);

        if(esEnroqueLargo(casillaOrigen, casillaDestino)){

            enrocarLargo(casillaOrigen, casillaDestino, piezaOrigen, piezaDestino);

        }else{

            enrocarCorto(casillaOrigen, casillaDestino, piezaOrigen, piezaDestino);

        }
    }
    private boolean esEnroqueLargo(Casilla casillaOrigen, Casilla casillaDestino) {
        return casillaOrigen.getNumLetra() - casillaDestino.getNumLetra() > 0;
    }
    private void enrocarLargo(Casilla casillaOrigen, Casilla casillaDestino, Pieza piezaOrigen, Pieza piezaDestino) {
        setPiezaEnCasilla(casillaOrigen, new PiezaNula());
        setPiezaEnCasilla(casillaDestino, new PiezaNula());
        setPiezaEnCasilla(getCasilla(Casilla.getLetra(casillaOrigen.getNumLetra() - 2), casillaOrigen.getNum()), piezaOrigen);
        setPiezaEnCasilla(getCasilla(Casilla.getLetra(casillaDestino.getNumLetra() + 3), casillaDestino.getNum()), piezaDestino);
    }
    private void enrocarCorto(Casilla casillaOrigen, Casilla casillaDestino, Pieza piezaOrigen, Pieza piezaDestino) {
        setPiezaEnCasilla(casillaOrigen, new PiezaNula());
        setPiezaEnCasilla(casillaDestino, new PiezaNula());

        setPiezaEnCasilla(getCasilla(Casilla.getLetra(casillaOrigen.getNumLetra() + 2), casillaOrigen.getNum()), piezaOrigen);
        setPiezaEnCasilla(getCasilla(Casilla.getLetra(casillaDestino.getNumLetra() - 2), casillaDestino.getNum()), piezaDestino);
    }
    public boolean reyQuedaEnJaqueAlEnrocar(Casilla casillaOrigen, Casilla casillaDestino) {

        if(esEnroqueLargo(casillaOrigen, casillaDestino))
            return reyQuedaEnJaqueAlEnrocarLargo(casillaOrigen);
        else
            return reyQuedaEnJaqueAlEnrocarCorto(casillaOrigen);
    }

    private boolean reyQuedaEnJaqueAlEnrocarCorto(Casilla casillaOrigen) {
        return reyQuedaEnJaque(casillaOrigen, new Casilla(Casilla.getLetra(casillaOrigen.getNumLetra() + 2), casillaOrigen.getNum()));
    }

    private boolean reyQuedaEnJaqueAlEnrocarLargo(Casilla casillaOrigen) {
        return reyQuedaEnJaque(casillaOrigen, new Casilla(Casilla.getLetra(casillaOrigen.getNumLetra() - 2), casillaOrigen.getNum()));
    }
}
