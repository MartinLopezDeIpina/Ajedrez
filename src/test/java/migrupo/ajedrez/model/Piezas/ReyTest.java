package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReyTest {

    static Rey rey;

    static Casilla casillaA, casillaB, casillaC, casillaD, casillaE, casillaF, casillaG, casillaH, casillaI, casillaJ;

    @BeforeAll
    static void setUp(){
        rey = new Rey(Color.BLANCO);

        casillaA = new Casilla('b', 1);

        casillaB = new Casilla('a', 0);
        casillaC = new Casilla('b', 0);
        casillaD = new Casilla('c', 0);
        casillaE = new Casilla('c', 1);
        casillaF = new Casilla('c', 2);
        casillaG = new Casilla('b', 2);
        casillaH = new Casilla('a', 2);
        casillaI = new Casilla('a', 1);

        casillaJ = new Casilla('b', 3);
    }

    @Test
    void puedeMoverseA() {
        assertTrue(rey.puedeMoverseA(casillaA, casillaB));
        assertTrue(rey.puedeMoverseA(casillaA, casillaC));
        assertTrue(rey.puedeMoverseA(casillaA, casillaD));
        assertTrue(rey.puedeMoverseA(casillaA, casillaE));
        assertTrue(rey.puedeMoverseA(casillaA, casillaF));
        assertTrue(rey.puedeMoverseA(casillaA, casillaG));
        assertTrue(rey.puedeMoverseA(casillaA, casillaH));
        assertTrue(rey.puedeMoverseA(casillaA, casillaI));

        assertFalse(rey.puedeMoverseA(casillaA, casillaJ));
    }
}