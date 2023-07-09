package migrupo.ajedrez.model.Piezas;

import migrupo.ajedrez.model.StateCasilla.Casilla;
import migrupo.ajedrez.model.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TorreTest {

    static Torre torre;

    static Casilla casillaA, casillaB, casillaC, casillaD;

    @BeforeAll
    static void setUp(){
        torre = new Torre(Color.BLANCO);

        casillaA = new Casilla('a', 0);
        casillaB = new Casilla('a', 7);

        casillaC = new Casilla('h', 7);
        casillaD = new Casilla('b', 1);
    }

    @Test
    void puedeMoverseA() {
        assertTrue(torre.puedeMoverseA(casillaA, casillaB));
        assertTrue(torre.puedeMoverseA(casillaA, casillaB));
        assertTrue(torre.puedeMoverseA(casillaB, casillaC));
        assertTrue(torre.puedeMoverseA(casillaC, casillaB));

        assertFalse(torre.puedeMoverseA(casillaA, casillaC));
        assertFalse(torre.puedeMoverseA(casillaC, casillaA));
        assertFalse(torre.puedeMoverseA(casillaA, casillaD));
        assertFalse(torre.puedeMoverseA(casillaD, casillaA));
    }
}