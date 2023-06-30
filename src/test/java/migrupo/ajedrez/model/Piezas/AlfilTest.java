package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;
import migrupo.ajedrez.model.Tablero;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlfilTest {

    static Alfil alfil;

    static Casilla casillaA;
    static Casilla casillaB;
    static Casilla casillaC;
    static Casilla casillaD;

    @BeforeAll
    static void setUp() {
        alfil = new Alfil(Color.BLANCO);

        casillaA = new Casilla('a', 0);
        casillaB = new Casilla('h', 7);
        casillaC = new Casilla('d', 2);
        casillaD = new Casilla('f', 4);
    }

    @Test
    void puedeMoverseA() {
        assertTrue(alfil.puedeMoverseA(casillaA, casillaB));
        assertTrue(alfil.puedeMoverseA(casillaB, casillaA));
        assertTrue(alfil.puedeMoverseA(casillaC, casillaD));
        assertTrue(alfil.puedeMoverseA(casillaD, casillaC));

        assertFalse(alfil.puedeMoverseA(casillaA, casillaC));
        assertFalse(alfil.puedeMoverseA(casillaA, casillaD));
        assertFalse(alfil.puedeMoverseA(casillaB, casillaC));
        assertFalse(alfil.puedeMoverseA(casillaB, casillaD));
    }
}