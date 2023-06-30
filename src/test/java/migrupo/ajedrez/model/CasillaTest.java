package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.PeonBlanco;
import migrupo.ajedrez.model.Piezas.PeonNegro;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CasillaTest {

    private static Tablero mTablero;
    private static Casilla[][] casillas;

   @BeforeAll
    static void setUp() throws NoSuchFieldException, IllegalAccessException {
       mTablero = Tablero.getInstance();

       iniciarCasillas();
    }

    private static void iniciarCasillas() throws NoSuchFieldException, IllegalAccessException {
        Field field = mTablero.getClass().getDeclaredField("casillas");
        field.setAccessible(true);
        casillas = (Casilla[][]) field.get(mTablero);
    }

    @Test
    void estaVacia() {
       casillas[0][0].setPieza(new PeonBlanco());

       assertFalse(casillas[0][0].estaVacia());
       assertTrue(casillas[0][1].estaVacia());
    }

    @Test
    void estaOcupada() {
         casillas[0][0].setPieza(new PeonBlanco());

         assertTrue(casillas[0][0].estaOcupada());
         assertFalse(casillas[0][1].estaOcupada());
    }

    @Test
    void getDistanciaVertical() {
       assertEquals(7, casillas[7][0].getDistanciaVertical(casillas[0][0]));
       assertEquals(-7, casillas[0][0].getDistanciaVertical(casillas[7][0]));
       assertEquals(7, casillas[7][0].getDistanciaVerticalAbs(casillas[0][4]));
    }

    @Test
    void getDistanciaVerticalAbs() {
        assertEquals(7, casillas[7][0].getDistanciaVerticalAbs(casillas[0][0]));
        assertEquals(7, casillas[0][0].getDistanciaVerticalAbs(casillas[7][0]));
        assertEquals(7, casillas[7][0].getDistanciaVerticalAbs(casillas[0][4]));
    }

    @Test
    void getDistanciaHorizontalAbs() {
        assertEquals(7, casillas[7][7].getDistanciaHorizontalAbs(casillas[7][0]));
        assertEquals(7, casillas[0][7].getDistanciaHorizontalAbs(casillas[7][0]));
        assertEquals(7, casillas[7][0].getDistanciaHorizontalAbs(casillas[7][7]));
    }

    @Test
    void getDistanciaHorizontal() {
        assertEquals(7, casillas[7][7].getDistanciaHorizontal(casillas[7][0]));
        assertEquals(7, casillas[0][7].getDistanciaHorizontal(casillas[7][0]));
        assertEquals(-7, casillas[7][0].getDistanciaHorizontal(casillas[7][7]));
    }

    @Test
    void hayPiezaColor() {
       casillas[0][0].setPieza(new PeonBlanco());
       casillas[0][1].setPieza(new PeonNegro());
       casillas[0][2].setPieza(null);

       assertTrue(casillas[0][0].hayPiezaColor(Color.BLANCO));
       assertFalse(casillas[0][0].hayPiezaColor(Color.NEGRO));
       assertTrue(casillas[0][1].hayPiezaColor(Color.NEGRO));
       assertFalse(casillas[0][1].hayPiezaColor(Color.BLANCO));
       assertFalse(casillas[0][2].hayPiezaColor(Color.NEGRO));
       assertFalse(casillas[0][2].hayPiezaColor(Color.BLANCO));
    }

    @Test
    void estaEnMismaColumna() {
        assertTrue(casillas[7][0].estaEnMismaColumna(casillas[0][0]));
        assertFalse(casillas[7][0].estaEnMismaColumna(casillas[0][1]));
    }

    @Test
    void estaEnMismaFila() {
       assertTrue(casillas[0][0].estaEnMismaFila(casillas[0][7]));
       assertFalse(casillas[0][0].estaEnMismaFila(casillas[1][7]));
    }

    @Test
    void estaEnMismaDiagonal() {
       assertTrue(casillas[0][0].estaEnMismaDiagonal(casillas[4][4]));
       assertTrue(casillas[4][4].estaEnMismaDiagonal(casillas[0][0]));
       assertTrue(casillas[4][1].estaEnMismaDiagonal(casillas[1][4]));

       assertFalse(casillas[0][0].estaEnMismaDiagonal(casillas[0][1]));
       assertFalse(casillas[3][0].estaEnMismaDiagonal(casillas[3][7]));
       assertFalse(casillas[7][0].estaEnMismaDiagonal(casillas[0][0]));
    }

    @Test
    void estaEnL() {
       assertTrue(casillas[3][3].estaEnL(casillas[1][2]));
       assertTrue(casillas[3][3].estaEnL(casillas[5][2]));
       assertTrue(casillas[3][3].estaEnL(casillas[5][4]));
       assertTrue(casillas[3][3].estaEnL(casillas[1][4]));

       assertFalse(casillas[3][3].estaEnL(casillas[1][1]));
       assertFalse(casillas[3][3].estaEnL(casillas[1][3]));
    }

    @Test
    void getDistanciaDiagonalAbs() {
         assertEquals(7, casillas[7][0].getDistanciaDiagonalAbs(casillas[0][7]));
         assertEquals(7, casillas[0][0].getDistanciaDiagonalAbs(casillas[7][7]));
         assertEquals(7, casillas[7][7].getDistanciaDiagonalAbs(casillas[0][0]));
         assertEquals(7, casillas[0][7].getDistanciaDiagonalAbs(casillas[7][0]));
    }
}