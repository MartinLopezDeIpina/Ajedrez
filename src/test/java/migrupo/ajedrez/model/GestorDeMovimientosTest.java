package migrupo.ajedrez.model;

import migrupo.ajedrez.model.BD.MovimientoDAOImpl;
import migrupo.ajedrez.model.BD.PartidaDAOImpl;
import migrupo.ajedrez.model.Piezas.Caballo;
import migrupo.ajedrez.model.Piezas.PeonBlanco;
import migrupo.ajedrez.model.Piezas.PeonNegro;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

class GestorDeMovimientosTest {

    static private GestorDeMovimientos mGestorDeMovimientos;
    static private MovimientoDAOImpl mMovimientoDAO;
    static private PartidaDAOImpl mPartidaDAO;

    static private Tablero mTablero;
    static private GestorDeTurnos mGestorDeTurnos;

    static Method getPiezaTablero;

    @BeforeAll
    static void setUp() throws NoSuchMethodException {
        mGestorDeMovimientos = GestorDeMovimientos.getInstance();
        mTablero = Tablero.getInstance();
        mGestorDeTurnos = GestorDeTurnos.getInstance();
        mMovimientoDAO = MovimientoDAOImpl.getInstance();
        mPartidaDAO = PartidaDAOImpl.getInstance();

        mGestorDeTurnos.iniciarPartida(new Jugador("pepe", "123"), new Jugador("juan", "123"));

        hacerGetPiezaAccesible();
    }

    @BeforeEach
    void setUpEach() {
        reiniciarTablero();
    }
    private static void hacerGetPiezaAccesible() throws NoSuchMethodException {
        getPiezaTablero = mTablero.getClass().getDeclaredMethod("getPiezaEnCasilla", Casilla.class);
        getPiezaTablero.setAccessible(true);
    }

    private static void reiniciarTablero() {
        mTablero.reiniciarTablero();
    }

    @Test
    void hacerMovimiento() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        casillaOrigenNoEstaVaciaTest();
        leTocaJugarAlJugadorCorrecto();
        casillaDestinoNoTienePiezaDelMismoColor();
        piezaPuedeMoverseACasillaDestino();
        noHayPiezasEntreCasillaOrigenYCasillaDestino();
        reyNoQuedaEnJaque();
    }
    private void casillaOrigenNoEstaVaciaTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Method casillaOrigenNoEstaVacia = getMetodo("casillaOrigenNoEstaVacia", Casilla.class);

        assertTrue((boolean) casillaOrigenNoEstaVacia.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 2)));
        assertTrue((boolean) casillaOrigenNoEstaVacia.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 7)));

        assertFalse((boolean) casillaOrigenNoEstaVacia.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 3)));
        assertFalse((boolean) casillaOrigenNoEstaVacia.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 6)));
    }

    private Method getMetodo(String nombreMetodo, Class clase) throws NoSuchMethodException {
        Method metodo = mGestorDeMovimientos.getClass().getDeclaredMethod(nombreMetodo, clase);
        metodo.setAccessible(true);
        return metodo;
    }
    private void leTocaJugarAlJugadorCorrecto() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method leTocaJugarAlJugadorCorrecto = getMetodo("leTocaJugarAlJugadorCorrecto", Casilla.class);

        assertTrue((boolean) leTocaJugarAlJugadorCorrecto.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 2)));
        assertFalse((boolean) leTocaJugarAlJugadorCorrecto.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 7)));

        mGestorDeTurnos.pasarTurno();

        assertFalse((boolean) leTocaJugarAlJugadorCorrecto.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 2)));
        assertTrue((boolean) leTocaJugarAlJugadorCorrecto.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 7)));

        mGestorDeTurnos.pasarTurno();
    }
    private void casillaDestinoNoTienePiezaDelMismoColor() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method casillaDestinoNoTienePiezaDelMismoColor = getMetodo("casillaDestinoNoTienePiezaDelMismoColor", Casilla.class);

        assertTrue((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 3)));
        assertTrue((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 7)));
        assertFalse((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 2)));

        mGestorDeTurnos.pasarTurno();

        assertTrue((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 2)));
        assertTrue((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 6)));
        assertFalse((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 7)));

        mGestorDeTurnos.pasarTurno();
    }
    private void piezaPuedeMoverseACasillaDestino() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method piezaPuedeMoverseACasillaDestino = getMetodo("piezaPuedeMoverseACasillaDestino", Movimiento.class);

        piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('a', 4)));

        assertTrue((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('a', 3))));
        assertTrue((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('a', 4))));
        assertFalse((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('a', 5))));
        assertFalse((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('a', 6))));

        assertTrue((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 7), mTablero.getCasilla('a', 6))));
        assertTrue((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 7), mTablero.getCasilla('a', 5))));
        assertFalse((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 7), mTablero.getCasilla('a', 4))));
        assertFalse((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 7), mTablero.getCasilla('a', 3))));
    }
    private void noHayPiezasEntreCasillaOrigenYCasillaDestino() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method noHayPiezasEntreCasillaOrigenYCasillaDestino = getMetodo("noHayPiezasEntreCasillaOrigenYCasillaDestino", Movimiento.class);

        mTablero.hacerMovimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('d', 4));

        assertTrue((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 3), mTablero.getCasilla('a', 6))));
        assertTrue((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 3), mTablero.getCasilla('h', 3))));
        assertTrue((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 3), mTablero.getCasilla('d', 6))));

        assertFalse((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('d', 3), mTablero.getCasilla('d', 6))));
        assertFalse((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 4), mTablero.getCasilla('h', 4))));
        assertFalse((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('c', 3), mTablero.getCasilla('f', 6))));

    }
    private void reyNoQuedaEnJaque() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method reyNoQuedaEnJaque = getMetodo("reyNoQuedaEnJaque", Movimiento.class);

        amenazarConPiezasNormales(reyNoQuedaEnJaque);

        amenazarConCaballo(reyNoQuedaEnJaque);
    }
    private void amenazarConPiezasNormales(Method reyNoQuedaEnJaque) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertTrue((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('e', 1), mTablero.getCasilla('e', 2))));

        mTablero.hacerMovimiento(mTablero.getCasilla('f', 2), mTablero.getCasilla('f', 3));
        mTablero.hacerMovimiento(mTablero.getCasilla('d', 8), mTablero.getCasilla('g', 3));

        assertFalse((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('f', 3), mTablero.getCasilla('f', 4))));
        assertTrue((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('f', 3), mTablero.getCasilla('f', 2))));
    }
    private void amenazarConCaballo(Method reyNoQuedaEnJaque) throws InvocationTargetException, IllegalAccessException {
        mTablero.hacerMovimiento(mTablero.getCasilla('g', 3), mTablero.getCasilla('d', 8));
        mTablero.hacerMovimiento(mTablero.getCasilla('b', 8), mTablero.getCasilla('f', 3));

        assertFalse((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('a', 3))));
        assertTrue((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('e', 2), mTablero.getCasilla('f', 3))));
    }

    @Test
    void setPartida() {

        int idPartida = crearPartidaPrueba();

        mGestorDeMovimientos.setPartida(idPartida);

        assertInstanceOf(PeonBlanco.class, mTablero.getCasilla('a', 4).getPieza());
        assertInstanceOf(PeonNegro.class, mTablero.getCasilla('a', 5).getPieza());
        assertInstanceOf(PeonBlanco.class, mTablero.getCasilla('b', 4).getPieza());
        assertInstanceOf(PeonNegro.class, mTablero.getCasilla('b', 5).getPieza());

        mPartidaDAO.eliminarPartida(idPartida);
    }

    private int crearPartidaPrueba(){
        int idPartida = mPartidaDAO.registrarPartida(new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

        mMovimientoDAO.guardarMovimiento(idPartida, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('a', 4)));
        mMovimientoDAO.guardarMovimiento(idPartida, new Movimiento(mTablero.getCasilla('a', 7), mTablero.getCasilla('a', 5)));
        mMovimientoDAO.guardarMovimiento(idPartida, new Movimiento(mTablero.getCasilla('b', 2), mTablero.getCasilla('b', 4)));
        mMovimientoDAO.guardarMovimiento(idPartida, new Movimiento(mTablero.getCasilla('b', 7), mTablero.getCasilla('b', 5)));

        return idPartida;
    }
}