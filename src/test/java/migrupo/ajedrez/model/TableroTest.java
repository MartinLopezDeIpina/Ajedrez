package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.*;
import migrupo.ajedrez.model.StateCasilla.Casilla;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    static Tablero mTablero;

    static Casilla[][] casillas;

    @BeforeAll
    static void setUp(){

        mTablero = Tablero.getInstance();

    }

    @BeforeEach
    void before() throws NoSuchFieldException, IllegalAccessException {
        iniciarTableroYCasillas();
    }

    private void iniciarTableroYCasillas() throws NoSuchFieldException, IllegalAccessException {
        mTablero.reiniciarTablero();
        iniciarCasillas();
    }

    @Test
    void ponerPosicionesIniciales() throws NoSuchFieldException, IllegalAccessException {

        comprobarBlancas();

        comprobarNegras();
    }
    private static void iniciarCasillas() throws NoSuchFieldException, IllegalAccessException {
        Field field = mTablero.getClass().getDeclaredField("casillas");
        field.setAccessible(true);
        casillas = (Casilla[][]) field.get(mTablero);
    }
    private void comprobarBlancas() {
        for(int i = 0; i < 8; i++){
            assertEquals(casillas[1][i].getPiezaValue().getClass(), PeonBlanco.class);
        }
        assertEquals(casillas[0][0].getPiezaValue().getClass(), Torre.class);
        assertEquals(casillas[0][7].getPiezaValue().getClass(), Torre.class);
        assertEquals(casillas[0][1].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
        assertEquals(casillas[0][6].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
        assertEquals(casillas[0][2].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
        assertEquals(casillas[0][5].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
        assertEquals(casillas[0][3].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Reina.class);
        assertEquals(casillas[0][4].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Rey.class);
    }
    private void comprobarNegras() {
        for(int i = 0; i < 8; i++){
            assertEquals(casillas[6][i].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.PeonNegro.class);
        }
        assertEquals(casillas[7][0].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Torre.class);
        assertEquals(casillas[7][7].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Torre.class);
        assertEquals(casillas[7][1].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
        assertEquals(casillas[7][6].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
        assertEquals(casillas[7][2].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
        assertEquals(casillas[7][5].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
        assertEquals(casillas[7][3].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Reina.class);
        assertEquals(casillas[7][4].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Rey.class);
    }

    @Test
    void hacerMovimiento() throws NoSuchFieldException, IllegalAccessException {

        mTablero.hacerMovimiento(casillas[1][0], casillas[2][0]);
        mTablero.hacerMovimiento(casillas[7][1], casillas[5][2]);

        iniciarCasillas();

        assertEquals(casillas[2][0].getPiezaValue().getClass(), PeonBlanco.class);
        assertEquals(casillas[5][2].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
    }

    @Test
    void getColorPiezaEnCasilla() throws NoSuchFieldException, IllegalAccessException {
        iniciarTableroYCasillas();

        assertEquals(mTablero.getColorPiezaEnCasilla(casillas[0][0]), Color.BLANCO);
        assertEquals(mTablero.getColorPiezaEnCasilla(casillas[1][1]), Color.BLANCO);
        assertEquals(mTablero.getColorPiezaEnCasilla(casillas[7][7]), Color.NEGRO);
        assertEquals(mTablero.getColorPiezaEnCasilla(casillas[7][0]), Color.NEGRO);
    }

    @Test
    void casillaVacia() throws NoSuchFieldException, IllegalAccessException {
        assertTrue(mTablero.casillaVacia(new Casilla('c', 3)));
        assertTrue(mTablero.casillaVacia(new Casilla('f', 4)));
        assertFalse(mTablero.casillaVacia(new Casilla('b', 0)));
        assertFalse(mTablero.casillaVacia(new Casilla('h', 7)));
    }

    @Test
    void hayPiezasEntreCasillaOrigenYCasillaDestino() throws NoSuchFieldException, IllegalAccessException {

        mTablero.hacerMovimiento(new Casilla('d', 1), new Casilla('d', 3));

        assertTrue(mTablero.hayPiezasEntreCasillaOrigenYCasillaDestino(new Casilla('a', 0), new Casilla('a', 2)));
        assertTrue(mTablero.hayPiezasEntreCasillaOrigenYCasillaDestino(new Casilla('a', 3), new Casilla('h', 3)));
        assertTrue(mTablero.hayPiezasEntreCasillaOrigenYCasillaDestino(new Casilla('a', 0), new Casilla('h', 7)));

        assertFalse(mTablero.hayPiezasEntreCasillaOrigenYCasillaDestino(new Casilla('a', 5), new Casilla('a', 2)));
        assertFalse(mTablero.hayPiezasEntreCasillaOrigenYCasillaDestino(new Casilla('a', 5), new Casilla('h', 5)));
        assertFalse(mTablero.hayPiezasEntreCasillaOrigenYCasillaDestino(new Casilla('d', 2), new Casilla('g', 5)));
    }

    @Test
    void reyQuedaEnJaque() throws NoSuchFieldException, IllegalAccessException {

        assertFalse(mTablero.reyQuedaEnJaque(mTablero.getCasilla('e', 1), mTablero.getCasilla('e', 2)));
        mTablero.hacerMovimiento(mTablero.getCasilla('e', 1), mTablero.getCasilla('e', 2));

        mTablero.hacerMovimiento(mTablero.getCasilla('h', 7), mTablero.getCasilla('e', 1));
        assertTrue(mTablero.reyQuedaEnJaque(mTablero.getCasilla('e', 2), mTablero.getCasilla('e', 3)));
        assertFalse(mTablero.reyQuedaEnJaque(mTablero.getCasilla('e', 0), mTablero.getCasilla('e', 1)));
        mTablero.hacerMovimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('e', 1));

        mTablero.hacerMovimiento(mTablero.getCasilla('a', 7), mTablero.getCasilla('e', 2));
        assertTrue(mTablero.reyQuedaEnJaque(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 2)));
        assertFalse(mTablero.reyQuedaEnJaque(mTablero.getCasilla('f', 0), mTablero.getCasilla('e', 2)));
    }

    @Test
    void vaciarTablero() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Method vaciarTablero = mTablero.getClass().getDeclaredMethod("vaciarTablero");
        vaciarTablero.setAccessible(true);
        vaciarTablero.invoke(mTablero);


        Arrays.stream(casillas).forEach(fila -> Arrays.stream(fila).forEach(casilla -> assertTrue(casilla.estaVacia())));
    }

    @Test
    void coronarTest(){
        mTablero.reiniciarTablero();

        mTablero.hacerMovimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 6));
        mTablero.hacerMovimiento(mTablero.getCasilla('b', 6), mTablero.getCasilla('b', 1));
        mTablero.coronar(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 7));
        mTablero.coronar(mTablero.getCasilla('b', 1), mTablero.getCasilla('c', 0));

        assertInstanceOf(Reina.class, mTablero.getCasilla('c', 0).getPiezaValue());
        assertInstanceOf(Reina.class, mTablero.getCasilla('a', 7).getPiezaValue());
    }

    @Test
    void getMovimientosPosiblesTest() throws NoSuchFieldException, IllegalAccessException {
        vaciarCasillas();


        mTablero.getCasilla('a', 1).setPieza(new PeonBlanco());
        mTablero.getCasilla('h', 0).setPieza(new Torre(Color.BLANCO));

        List<Casilla[]> movimientosEsperados = Arrays.asList(
                new Casilla[]{mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 2)},
                new Casilla[]{mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 3)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('h', 1)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('h', 2)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('h', 3)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('h', 4)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('h', 5)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('h', 6)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('h', 7)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('g', 0)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('f', 0)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('e', 0)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('d', 0)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('c', 0)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('b', 0)},
                new Casilla[]{mTablero.getCasilla('h', 0), mTablero.getCasilla('a', 0)}
        );

        List<Casilla[]> movimientosPosibles = mTablero.getMovimientosPosibles(Color.BLANCO);

        assertEquals(movimientosEsperados.size(), movimientosPosibles.size());
    }

    private void vaciarCasillas() {
        Arrays.stream(casillas).flatMap(Arrays::stream).forEach(casilla -> casilla.setPieza(new PiezaNula()));
    }

    @Test
    void materialInsuficienteTest(){
        dejarSoloReyes();

        probarSoloReyes();
        probarUnPeon();

        dejarSoloReyes();
        probarUnCaballo();

        dejarSoloReyes();
        probarUnAlfil();

        dejarSoloReyes();
        probarUnCaballoYAlfil();

        dejarSoloReyes();
        probarDosCaballos();

        dejarSoloReyes();
        probarDosAlfiles();

    }
    private void dejarSoloReyes() {
        mTablero.vaciarTablero();

        mTablero.getCasilla('a', 0).setPieza(new Rey(Color.BLANCO));
        mTablero.getCasilla('a', 7).setPieza(new Rey(Color.NEGRO));
    }
    private void probarUnPeon() {
        mTablero.getCasilla('b', 0).setPieza(new PeonBlanco());

        assertFalse(mTablero.materialInsuficiente());
    }
    private void probarSoloReyes() {
        assertTrue(mTablero.materialInsuficiente());
    }
    private void probarDosAlfiles() {
        mTablero.getCasilla('b', 0).setPieza(new Alfil(Color.BLANCO));
        mTablero.getCasilla('g', 0).setPieza(new Alfil(Color.BLANCO));

        assertFalse(mTablero.materialInsuficiente());
    }
    private void probarDosCaballos() {
        mTablero.getCasilla('b', 0).setPieza(new Caballo(Color.BLANCO));
        mTablero.getCasilla('g', 0).setPieza(new Caballo(Color.BLANCO));

        assertTrue(mTablero.materialInsuficiente());
    }
    private void probarUnCaballoYAlfil() {
        mTablero.getCasilla('b', 0).setPieza(new Caballo(Color.BLANCO));
        mTablero.getCasilla('c', 0).setPieza(new Alfil(Color.BLANCO));

        assertFalse(mTablero.materialInsuficiente());
    }
    private void probarUnAlfil() {
        mTablero.getCasilla('b', 0).setPieza(new Alfil(Color.BLANCO));

        assertTrue(mTablero.materialInsuficiente());
    }
    private void probarUnCaballo() {
        mTablero.getCasilla('b', 0).setPieza(new Caballo(Color.BLANCO));

        assertTrue(mTablero.materialInsuficiente());
    }

    @Test
    void deshacerEnroqueTest(){
        mTablero.vaciarTablero();

        mTablero.getCasilla('e', 0).setPieza(new Rey(Color.BLANCO));
        mTablero.getCasilla('a', 0).setPieza(new Torre(Color.BLANCO));
        mTablero.getCasilla('h', 0).setPieza(new Torre(Color.BLANCO));

        testDeshacerEnroqueLargo();

        testDeshacerEnroqueCorto();
    }

    private void testDeshacerEnroqueLargo() {
        mTablero.hacerMovimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('a', 0));
        mTablero.deshacerEnroque(mTablero.getCasilla('e', 0), mTablero.getCasilla('a', 0));

        assertInstanceOf(Torre.class, mTablero.getCasilla('a', 0).getPiezaValue());
        assertInstanceOf(PiezaNula.class, mTablero.getCasilla('b', 0).getPiezaValue());
        assertInstanceOf(PiezaNula.class, mTablero.getCasilla('c', 0).getPiezaValue());
        assertInstanceOf(PiezaNula.class, mTablero.getCasilla('d', 0).getPiezaValue());
        assertInstanceOf(Rey.class, mTablero.getCasilla('e', 0).getPiezaValue());
    }

    private void testDeshacerEnroqueCorto() {
        mTablero.hacerMovimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('h', 0));
        mTablero.deshacerEnroque(mTablero.getCasilla('e', 0), mTablero.getCasilla('h', 0));

        assertInstanceOf(Torre.class, mTablero.getCasilla('h', 0).getPiezaValue());
        assertInstanceOf(PiezaNula.class, mTablero.getCasilla('g', 0).getPiezaValue());
        assertInstanceOf(PiezaNula.class, mTablero.getCasilla('f', 0).getPiezaValue());
        assertInstanceOf(Rey.class, mTablero.getCasilla('e', 0).getPiezaValue());
    }


}