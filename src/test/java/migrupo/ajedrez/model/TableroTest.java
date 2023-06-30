package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.PeonBlanco;
import migrupo.ajedrez.model.Piezas.Torre;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

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
            assertEquals(casillas[1][i].getPieza().getClass(), PeonBlanco.class);
        }
        assertEquals(casillas[0][0].getPieza().getClass(), Torre.class);
        assertEquals(casillas[0][7].getPieza().getClass(), Torre.class);
        assertEquals(casillas[0][1].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
        assertEquals(casillas[0][6].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
        assertEquals(casillas[0][2].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
        assertEquals(casillas[0][5].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
        assertEquals(casillas[0][3].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Reina.class);
        assertEquals(casillas[0][4].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Rey.class);
    }
    private void comprobarNegras() {
        for(int i = 0; i < 8; i++){
            assertEquals(casillas[6][i].getPieza().getClass(), migrupo.ajedrez.model.Piezas.PeonNegro.class);
        }
        assertEquals(casillas[7][0].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Torre.class);
        assertEquals(casillas[7][7].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Torre.class);
        assertEquals(casillas[7][1].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
        assertEquals(casillas[7][6].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Caballo.class);
        assertEquals(casillas[7][2].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
        assertEquals(casillas[7][5].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
        assertEquals(casillas[7][3].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Reina.class);
        assertEquals(casillas[7][4].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Rey.class);
    }

    @Test
    void hacerMovimiento() throws NoSuchFieldException, IllegalAccessException {
        mTablero.ponerPosicionesIniciales();
        iniciarCasillas();

        //todo: aqui falla algo
        mTablero.hacerMovimiento(casillas[1][0], casillas[2][0]);
        mTablero.hacerMovimiento(casillas[7][1], casillas[5][3]);

        iniciarCasillas();

        assertEquals(casillas[2][0].getPieza().getClass(), PeonBlanco.class);
        assertEquals(casillas[5][3].getPieza().getClass(), migrupo.ajedrez.model.Piezas.Alfil.class);
    }

    @Test
    void getColorPiezaEnCasilla() {
    }

    @Test
    void casillaVacia() {
    }

    @Test
    void hayPiezasEntreCasillaOrigenYCasillaDestino() {
    }

    @Test
    void reyQuedaEnJaque() {
    }
}