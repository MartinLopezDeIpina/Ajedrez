package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaballoTest {

    static Caballo caballo;

    static Casilla casillaA, casillaB, casillaC, casillaD, casillaE, casillaF, casillaG, casillaH, casillaI, casillaJ, casillaK;

    @BeforeAll
    static void setUp() {
        caballo = new Caballo(Color.BLANCO);

        casillaA = new Casilla('d', 3);
        casillaB = new Casilla('e', 5);
        casillaC = new Casilla('f', 4);
        casillaD = new Casilla('f', 2);
        casillaE = new Casilla('e', 1);
        casillaF = new Casilla('c', 1);
        casillaG = new Casilla('b', 2);
        casillaH = new Casilla('b', 4);
        casillaI = new Casilla('c', 5);

        casillaJ = new Casilla('a', 0);
        casillaK = new Casilla('c', 4);
    }


    @Test
    void puedeMoverseA() {
        assertTrue(caballo.puedeMoverseA(casillaA, casillaB));
        assertTrue(caballo.puedeMoverseA(casillaA, casillaC));
        assertTrue(caballo.puedeMoverseA(casillaA, casillaD));
        assertTrue(caballo.puedeMoverseA(casillaA, casillaE));
        assertTrue(caballo.puedeMoverseA(casillaA, casillaF));
        assertTrue(caballo.puedeMoverseA(casillaA, casillaG));
        assertTrue(caballo.puedeMoverseA(casillaA, casillaH));
        assertTrue(caballo.puedeMoverseA(casillaA, casillaI));

        assertFalse(caballo.puedeMoverseA(casillaA, casillaJ));
        assertFalse(caballo.puedeMoverseA(casillaA, casillaK));
        assertFalse(caballo.puedeMoverseA(casillaB, casillaC));
    }
}