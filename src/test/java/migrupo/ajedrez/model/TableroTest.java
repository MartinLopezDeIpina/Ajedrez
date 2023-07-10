package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.PeonBlanco;
import migrupo.ajedrez.model.Piezas.Torre;
import migrupo.ajedrez.model.StateCasilla.Casilla;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    static Tablero mTablero;

    static Casilla[][] casillas;

    @BeforeAll
    static void setUp(){

        mTablero = Tablero.getInstance();

    }

    @Test
    void ponerPosicionesIniciales() throws NoSuchFieldException, IllegalAccessException {

        mTablero.ponerPosicionesIniciales();

        iniciarCasillas();

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
        iniciarTableroYCasillas();

        mTablero.hacerMovimiento(casillas[1][0], casillas[2][0]);
        mTablero.hacerMovimiento(casillas[7][1], casillas[5][2]);

        iniciarCasillas();

        assertEquals(casillas[2][0].getPiezaValue().getClass(), PeonBlanco.class);
        assertEquals(casillas[5][2].getPiezaValue().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
    }

    private void iniciarTableroYCasillas() throws NoSuchFieldException, IllegalAccessException {
        mTablero.reiniciarTablero();
        iniciarCasillas();
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
        iniciarTableroYCasillas();

        assertTrue(mTablero.casillaVacia(new Casilla('c', 3)));
        assertTrue(mTablero.casillaVacia(new Casilla('f', 4)));
        assertFalse(mTablero.casillaVacia(new Casilla('b', 0)));
        assertFalse(mTablero.casillaVacia(new Casilla('h', 7)));
    }

    @Test
    void hayPiezasEntreCasillaOrigenYCasillaDestino() throws NoSuchFieldException, IllegalAccessException {
        iniciarTableroYCasillas();

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
        iniciarTableroYCasillas();

        assertFalse(mTablero.reyQuedaEnJaque(new Casilla('e', 1), new Casilla('e', 2)));

        mTablero.hacerMovimiento(new Casilla('h', 7), new Casilla('e', 1));
        assertTrue(mTablero.reyQuedaEnJaque(new Casilla('e', 2), new Casilla('e', 3)));
        assertFalse(mTablero.reyQuedaEnJaque(new Casilla('e', 0), new Casilla('e', 1)));

        mTablero.hacerMovimiento(new Casilla('b', 7), new Casilla('d', 2));
        assertTrue(mTablero.reyQuedaEnJaque(new Casilla('e', 3), new Casilla('e', 4)));
        assertFalse(mTablero.reyQuedaEnJaque(new Casilla('e', 0), new Casilla('e', 1)));
    }

    @Test
    void vaciarTablero() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        iniciarTableroYCasillas();

        Method vaciarTablero = mTablero.getClass().getDeclaredMethod("vaciarTablero");
        vaciarTablero.setAccessible(true);
        vaciarTablero.invoke(mTablero);


        Arrays.stream(casillas).forEach(fila -> Arrays.stream(fila).forEach(casilla -> assertTrue(casilla.estaVacia())));
    }
}