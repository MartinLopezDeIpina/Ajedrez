package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReinaTest {

    static Reina reina;

    static Casilla casillaA, casillaB, casillaC, casillaD, casillaE, casillaF, casillaG, casillaH, casillaI, casillaJ, casillaK, casillaL, casillaM, casillaN;

    @BeforeAll
    static void setUp(){
        reina = new Reina(Color.BLANCO);

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
        casillaK = new Casilla('h', 7);
        casillaL = new Casilla('h', 1);

        casillaM = new Casilla('f', 3);
        casillaN = new Casilla('d', 5);
    }

    @Test
    void puedeMoverseA() {
        assertTrue(reina.puedeMoverseA(casillaA, casillaB));
        assertTrue(reina.puedeMoverseA(casillaA, casillaC));
        assertTrue(reina.puedeMoverseA(casillaA, casillaD));
        assertTrue(reina.puedeMoverseA(casillaA, casillaE));
        assertTrue(reina.puedeMoverseA(casillaA, casillaF));
        assertTrue(reina.puedeMoverseA(casillaA, casillaG));
        assertTrue(reina.puedeMoverseA(casillaA, casillaH));
        assertTrue(reina.puedeMoverseA(casillaA, casillaI));
        assertTrue(reina.puedeMoverseA(casillaA, casillaJ));
        assertTrue(reina.puedeMoverseA(casillaA, casillaK));
        assertTrue(reina.puedeMoverseA(casillaA, casillaL));

        assertFalse(reina.puedeMoverseA(casillaA, casillaM));
        assertFalse(reina.puedeMoverseA(casillaA, casillaN));
    }
}