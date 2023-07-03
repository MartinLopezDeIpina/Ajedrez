package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.Casilla;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeonTest {

    static PeonBlanco peonB;
    static PeonNegro peonN;

    static Casilla casillaA, casillaB, casillaC, casillaD, casillaE, casillaF, casillaG, casillaH, casillaI, casillaJ, casillaK, casillaL, casillaM, casillaN;

    @BeforeAll
    static void setUp(){
        setUpBlanco();
        setUpNegro();
    }
    private static void setUpBlanco(){
        peonB = new PeonBlanco();

        casillaA = new Casilla('b', 1);

        casillaB = new Casilla('b', 2);
        casillaC = new Casilla('b', 3);
        casillaE = new Casilla('a', 2);

        casillaD = new Casilla('b', 4);
        casillaF = new Casilla('c', 3);
        casillaG = new Casilla('b', 0);
    }
    private static void setUpNegro(){
        peonN = new PeonNegro();

        casillaH = new Casilla('b', 6);

        casillaI = new Casilla('b', 5);
        casillaJ = new Casilla('b', 4);
        casillaL = new Casilla('a', 5);

        casillaK = new Casilla('b', 3);
        casillaM = new Casilla('c', 5);
        casillaN = new Casilla('b', 7);
    }

    @Test
    void puedeMoverseA() {
        testBlanco();
        testNegro();
    }

    private void testBlanco(){

        testBlancoVertical();

        testBlancoDiagonal();
    }
    private void testBlancoVertical() {
        assertTrue(peonB.puedeMoverseA(casillaA, casillaB));
        assertTrue(peonB.puedeMoverseA(casillaA, casillaC));

        assertFalse(peonB.puedeMoverseA(casillaA, casillaD));
        assertFalse(peonB.puedeMoverseA(casillaA, casillaF));
        assertFalse(peonB.puedeMoverseA(casillaA, casillaG));
    }
    private void testBlancoDiagonal() {
        casillaE.setPieza(new PeonNegro());
        assertTrue(peonB.puedeMoverseA(casillaA, casillaE));

        casillaE.setPieza(new PiezaNula());
        assertFalse(peonB.puedeMoverseA(casillaA, casillaE));

        casillaE.setPieza(new PeonBlanco());
        assertFalse(peonB.puedeMoverseA(casillaA, casillaE));
    }

    private void testNegro(){

        testNegroVertical();

        testNegroDiagonal();
    }
    private void testNegroVertical() {
        assertTrue(peonN.puedeMoverseA(casillaH, casillaI));
        assertTrue(peonN.puedeMoverseA(casillaH, casillaJ));

        assertFalse(peonN.puedeMoverseA(casillaH, casillaK));
        assertFalse(peonN.puedeMoverseA(casillaH, casillaM));
        assertFalse(peonN.puedeMoverseA(casillaH, casillaN));
    }
    private void testNegroDiagonal() {
        casillaL.setPieza(new PeonBlanco());
        assertTrue(peonN.puedeMoverseA(casillaH, casillaL));

        casillaL.setPieza(new PiezaNula());
        assertFalse(peonN.puedeMoverseA(casillaH, casillaL));

        casillaL.setPieza(new PeonNegro());
        assertFalse(peonN.puedeMoverseA(casillaH, casillaL));
    }
}