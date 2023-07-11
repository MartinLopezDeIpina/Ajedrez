package migrupo.ajedrez.model;

import migrupo.ajedrez.model.Piezas.Alfil;
import migrupo.ajedrez.model.StateCasilla.Casilla;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoTest {

    static Movimiento movimientoA, movimientoB, movimientoC, movimientoD;

    static Casilla casillaA;

    @BeforeAll
    static void setUp() {
        casillaA = new Casilla('a', 0);
        casillaA.setPieza(new Alfil(Color.NEGRO));

        movimientoA = new Movimiento(casillaA, new Casilla('h', 7));
        movimientoB = new Movimiento(casillaA, new Casilla('d', 3));

        movimientoC = new Movimiento(casillaA, new Casilla('a', 1));
        movimientoD = new Movimiento(casillaA, new Casilla('h', 0));
    }

    @Test
    void piezaPuedeMoverseACasillaDestino() {
        assertTrue(movimientoA.piezaPuedeMoverseACasillaDestino());
        assertTrue(movimientoB.piezaPuedeMoverseACasillaDestino());

        assertFalse(movimientoC.piezaPuedeMoverseACasillaDestino());
        assertFalse(movimientoD.piezaPuedeMoverseACasillaDestino());
    }

    @Test
    void getNumDestinoTest(){
        assertEquals(7, movimientoA.getNumDestino());
        assertEquals(3, movimientoB.getNumDestino());
    }
}