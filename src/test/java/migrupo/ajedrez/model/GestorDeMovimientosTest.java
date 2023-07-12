package migrupo.ajedrez.model;

import migrupo.ajedrez.model.BD.ConexionBD;
import migrupo.ajedrez.model.BD.MovimientoDAOImpl;
import migrupo.ajedrez.model.BD.PartidaDAOImpl;
import migrupo.ajedrez.model.Piezas.PeonBlanco;
import migrupo.ajedrez.model.Piezas.PeonNegro;
import migrupo.ajedrez.model.StateCasilla.Casilla;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

class GestorDeMovimientosTest {

    static private GestorDeMovimientos mGestorDeMovimientos;
    static private MovimientoDAOImpl mMovimientoDAO;
    static private PartidaDAOImpl mPartidaDAO;
    private static ConexionBD mConexionBD;

    static private Tablero mTablero;
    static private GestorDeTurnos mGestorDeTurnos;
    static private Partida mPartida;

    static Method getPiezaTablero;
    static Field casillaSeleccionada;
    static Method coronarSiPosible, esJackeMate;


    @BeforeAll
    static void setUp() throws NoSuchMethodException, NoSuchFieldException {
        mConexionBD = ConexionBD.getInstance();
        mGestorDeMovimientos = GestorDeMovimientos.getInstance();
        mTablero = Tablero.getInstance();
        mGestorDeTurnos = GestorDeTurnos.getInstance();
        mMovimientoDAO = MovimientoDAOImpl.getInstance();
        mPartidaDAO = PartidaDAOImpl.getInstance();
        mPartida = Partida.getInstance();

        mConexionBD.establecerConexion();

        hacerGetPiezaAccesible();
        hacerCasillaSeleccionadaAccesible();
        hacerCoronarSiPosibleAccesible();
        hacerEsJackeMateAccesible();
    }

    private static void hacerCoronarSiPosibleAccesible() throws NoSuchMethodException {
        coronarSiPosible = mGestorDeMovimientos.getClass().getDeclaredMethod("coronarSiPosible", Movimiento.class);
        coronarSiPosible.setAccessible(true);
    }
    private static void hacerGetPiezaAccesible() throws NoSuchMethodException {
        getPiezaTablero = mTablero.getClass().getDeclaredMethod("getPiezaEnCasilla", Casilla.class);
        getPiezaTablero.setAccessible(true);
    }
    private static void hacerCasillaSeleccionadaAccesible() throws NoSuchFieldException {
        casillaSeleccionada = mGestorDeMovimientos.getClass().getDeclaredField("casillaSeleccionada");
        casillaSeleccionada.setAccessible(true);
    }
    private static void hacerEsJackeMateAccesible() throws NoSuchMethodException {
        esJackeMate = mGestorDeMovimientos.getClass().getDeclaredMethod("esJackeMate");
        esJackeMate.setAccessible(true);
    }

    @AfterAll
    static void tearDown() {
        mConexionBD.cerrarConexion();
    }

    @BeforeEach
    void setUpEach() {
        reiniciarTablero();
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

        assertTrue((boolean) casillaOrigenNoEstaVacia.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 1)));
        assertTrue((boolean) casillaOrigenNoEstaVacia.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 6)));

        assertFalse((boolean) casillaOrigenNoEstaVacia.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 2)));
        assertFalse((boolean) casillaOrigenNoEstaVacia.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 5)));
    }

    private Method getMetodo(String nombreMetodo, Class clase) throws NoSuchMethodException {
        Method metodo = mGestorDeMovimientos.getClass().getDeclaredMethod(nombreMetodo, clase);
        metodo.setAccessible(true);
        return metodo;
    }
    private void leTocaJugarAlJugadorCorrecto() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method leTocaJugarAlJugadorCorrecto = getMetodo("leTocaJugarAlJugadorCorrecto", Casilla.class);

        assertTrue((boolean) leTocaJugarAlJugadorCorrecto.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 1)));
        assertFalse((boolean) leTocaJugarAlJugadorCorrecto.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 6)));

        mGestorDeTurnos.pasarTurno();

        assertFalse((boolean) leTocaJugarAlJugadorCorrecto.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 1)));
        assertTrue((boolean) leTocaJugarAlJugadorCorrecto.invoke(mGestorDeMovimientos, mTablero.getCasilla('a', 6)));

        mGestorDeTurnos.pasarTurno();
    }
    private void casillaDestinoNoTienePiezaDelMismoColor() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method casillaDestinoNoTienePiezaDelMismoColor = getMetodo("casillaDestinoNoTienePiezaDelMismoColor", Movimiento.class);

        assertTrue((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 2))));
        assertTrue((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos,new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 5))));
        assertFalse((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('b', 1))));


        assertTrue((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 5))));
        assertTrue((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 2))));
        assertFalse((boolean) casillaDestinoNoTienePiezaDelMismoColor.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('b', 6))));

    }
    private void piezaPuedeMoverseACasillaDestino() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method piezaPuedeMoverseACasillaDestino = getMetodo("piezaPuedeMoverseACasillaDestino", Movimiento.class);

        piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 3)));

        assertTrue((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 2))));
        assertTrue((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 3))));
        assertFalse((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 4))));
        assertFalse((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 5))));

        assertTrue((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 5))));
        assertTrue((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 4))));
        assertFalse((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 3))));
        assertFalse((boolean) piezaPuedeMoverseACasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 2))));
    }
    private void noHayPiezasEntreCasillaOrigenYCasillaDestino() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method noHayPiezasEntreCasillaOrigenYCasillaDestino = getMetodo("noHayPiezasEntreCasillaOrigenYCasillaDestino", Movimiento.class);

        mTablero.hacerMovimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('d', 3));

        assertTrue((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('a', 5))));
        assertTrue((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('h', 2))));
        assertTrue((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 2), mTablero.getCasilla('d', 5))));

        assertFalse((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('d', 2), mTablero.getCasilla('d', 5))));
        assertFalse((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 3), mTablero.getCasilla('h', 3))));
        assertFalse((boolean) noHayPiezasEntreCasillaOrigenYCasillaDestino.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('c', 2), mTablero.getCasilla('f', 5))));

    }
    private void reyNoQuedaEnJaque() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method reyNoQuedaEnJaque = getMetodo("reyNoQuedaEnJaque", Movimiento.class);

        amenazarConPiezasNormales(reyNoQuedaEnJaque);

        amenazarConCaballo(reyNoQuedaEnJaque);
    }
    private void amenazarConPiezasNormales(Method reyNoQuedaEnJaque) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertTrue((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('e', 1))));

        mTablero.hacerMovimiento(mTablero.getCasilla('f', 1), mTablero.getCasilla('f', 2));
        mTablero.hacerMovimiento(mTablero.getCasilla('d', 7), mTablero.getCasilla('g', 2));

        assertFalse((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('f', 2), mTablero.getCasilla('f', 3))));
        assertTrue((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('f', 2), mTablero.getCasilla('f', 1))));
    }
    private void amenazarConCaballo(Method reyNoQuedaEnJaque) throws InvocationTargetException, IllegalAccessException {
        mTablero.hacerMovimiento(mTablero.getCasilla('g', 2), mTablero.getCasilla('d', 7));
        mTablero.hacerMovimiento(mTablero.getCasilla('b', 7), mTablero.getCasilla('f', 2));

        assertFalse((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('h', 1), mTablero.getCasilla('h', 2))));
        assertTrue((boolean) reyNoQuedaEnJaque.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('e', 1), mTablero.getCasilla('f', 2))));
    }

    @Test
    void setPartida() {

        int idPartida = crearPartidaPrueba();

        mPartida.setPartida(idPartida, new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

        assertInstanceOf(PeonBlanco.class, mTablero.getCasilla('a', 3).getPiezaValue());
        assertInstanceOf(PeonNegro.class, mTablero.getCasilla('a', 4).getPiezaValue());
        assertInstanceOf(PeonBlanco.class, mTablero.getCasilla('b', 3).getPiezaValue());
        assertInstanceOf(PeonNegro.class, mTablero.getCasilla('b', 4).getPiezaValue());

        mPartidaDAO.eliminarPartida(idPartida);
    }

    private int crearPartidaPrueba(){

        int idPartida = mPartidaDAO.registrarPartida(new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

        mMovimientoDAO.guardarMovimiento(idPartida, new Movimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 3)));
        mMovimientoDAO.guardarMovimiento(idPartida, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 4)));
        mMovimientoDAO.guardarMovimiento(idPartida, new Movimiento(mTablero.getCasilla('b', 1), mTablero.getCasilla('b', 3)));
        mMovimientoDAO.guardarMovimiento(idPartida, new Movimiento(mTablero.getCasilla('b', 6), mTablero.getCasilla('b', 4)));

        return idPartida;
    }

    @Test
    void casillaSeleccionadaTest() throws IllegalAccessException {

        assertNull(casillaSeleccionada.get(mGestorDeMovimientos));

        mGestorDeMovimientos.casillaSeleccionada(mTablero.getCasilla('a', 1));
        assertEquals(mTablero.getCasilla('a', 1), casillaSeleccionada.get(mGestorDeMovimientos));

        mGestorDeMovimientos.casillaSeleccionada(mTablero.getCasilla('a', 2));
        assertNull(casillaSeleccionada.get(mGestorDeMovimientos));
    }

    @Test
    void desseleccionarTest() throws IllegalAccessException {
        mGestorDeMovimientos.casillaSeleccionada(mTablero.getCasilla('a', 1));
        mGestorDeMovimientos.desseleccionar();

        assertNull(casillaSeleccionada.get(mGestorDeMovimientos));
    }

    @Test
    void coronarSiPosibleTest() throws InvocationTargetException, IllegalAccessException {
        mTablero.hacerMovimiento(mTablero.getCasilla('a', 1), mTablero.getCasilla('a', 6));
        mTablero.hacerMovimiento(mTablero.getCasilla('c', 6), mTablero.getCasilla('c', 1));

        assertTrue((Boolean) coronarSiPosible.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 7))));
        assertTrue((Boolean) coronarSiPosible.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('c', 1), mTablero.getCasilla('b', 0))));
        assertFalse((Boolean) coronarSiPosible.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('b', 1), mTablero.getCasilla('b', 2))));
    }

    @Test
    void esJackeMateTest() throws InvocationTargetException, IllegalAccessException {
        reiniciarTablero();

        probarMatePastor();

        reiniciarTablero();

        probarJackesSinMate();


        

    }

    private void probarMatePastor() throws InvocationTargetException, IllegalAccessException {
        int identificador = mPartidaDAO.registrarPartida(new Jugador("pepe", "123"), new Jugador("pepe2", "123"));
        mPartida.setPartida(identificador, new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

        mTablero.hacerMovimiento(mTablero.getCasilla('e', 1), mTablero.getCasilla('e', 3));
        mTablero.hacerMovimiento(mTablero.getCasilla('e', 6), mTablero.getCasilla('e', 4));
        mTablero.hacerMovimiento(mTablero.getCasilla('d', 0), mTablero.getCasilla('h', 4));
        mTablero.hacerMovimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 5));
        mTablero.hacerMovimiento(mTablero.getCasilla('f', 0), mTablero.getCasilla('c', 3));
        mTablero.hacerMovimiento(mTablero.getCasilla('a', 5), mTablero.getCasilla('a', 4));
        mTablero.hacerMovimiento(mTablero.getCasilla('h', 4), mTablero.getCasilla('f', 6));

        assertTrue((Boolean) esJackeMate.invoke(mGestorDeMovimientos));
    }

    private void probarJackesSinMate() throws InvocationTargetException, IllegalAccessException {

        mTablero.hacerMovimiento(mTablero.getCasilla('e', 1), mTablero.getCasilla('e', 3));
        mTablero.hacerMovimiento(mTablero.getCasilla('e', 6), mTablero.getCasilla('e', 4));
        mTablero.hacerMovimiento(mTablero.getCasilla('d', 0), mTablero.getCasilla('h', 4));
        mTablero.hacerMovimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 5));
        mTablero.hacerMovimiento(mTablero.getCasilla('h', 4), mTablero.getCasilla('f', 6));

        assertFalse((Boolean) esJackeMate.invoke(mGestorDeMovimientos));

        mTablero.hacerMovimiento(mTablero.getCasilla('e', 7), mTablero.getCasilla('f', 6));
        mTablero.hacerMovimiento(mTablero.getCasilla('f', 0), mTablero.getCasilla('c', 3));

        assertFalse((Boolean) esJackeMate.invoke(mGestorDeMovimientos));
    }
}