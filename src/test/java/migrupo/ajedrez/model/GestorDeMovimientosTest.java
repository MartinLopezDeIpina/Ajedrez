package migrupo.ajedrez.model;

import migrupo.ajedrez.model.BD.ConexionBD;
import migrupo.ajedrez.model.BD.MovimientoDAOImpl;
import migrupo.ajedrez.model.BD.PartidaDAOImpl;
import migrupo.ajedrez.model.Piezas.*;
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
    static Field casillaSeleccionada, razonVictoria;
    static Method coronar, comprobarFinPartida;


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
        hacerCoronarAccesible();
        hacerComprobarFinPartidaAccesible();
        hacerRazonVictoriaAccesible();
    }

    private static void hacerRazonVictoriaAccesible() throws NoSuchFieldException {
        razonVictoria = mPartida.getClass().getDeclaredField("razonVictoria");
        razonVictoria.setAccessible(true);
    }
    private static void hacerCoronarAccesible() throws NoSuchMethodException {
        coronar = mGestorDeMovimientos.getClass().getDeclaredMethod("coronar", Movimiento.class);
        coronar.setAccessible(true);
    }
    private static void hacerGetPiezaAccesible() throws NoSuchMethodException {
        getPiezaTablero = mTablero.getClass().getDeclaredMethod("getPiezaEnCasillaValue", Casilla.class);
        getPiezaTablero.setAccessible(true);
    }
    private static void hacerCasillaSeleccionadaAccesible() throws NoSuchFieldException {
        casillaSeleccionada = mGestorDeMovimientos.getClass().getDeclaredField("casillaSeleccionada");
        casillaSeleccionada.setAccessible(true);
    }
    private static void hacerComprobarFinPartidaAccesible() throws NoSuchMethodException {
        comprobarFinPartida = mGestorDeMovimientos.getClass().getDeclaredMethod("comprobarFinPartida");
        comprobarFinPartida.setAccessible(true);
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

        int idPartida = mPartida.iniciarPartidaNueva(new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

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

        coronar.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 7)));
        assertInstanceOf(Reina.class, mTablero.getCasilla('a', 7).getPiezaValue());
        coronar.invoke(mGestorDeMovimientos, new Movimiento(mTablero.getCasilla('c', 1), mTablero.getCasilla('b', 0)));
        assertInstanceOf(Reina.class, mTablero.getCasilla('b', 0).getPiezaValue());

    }

    @Test
    void comprobarFinPartidaTest() throws InvocationTargetException, IllegalAccessException {
        reiniciarTablero();

        probarMatePastor();

        reiniciarTablero();

        probarJackesSinMate();

        mTablero.vaciarTablero();

        probarReyAhogado();

        probarMaterialInsuficiente();

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

        razonVictoria = null;
        comprobarFinPartida.invoke(mGestorDeMovimientos);

        assertEquals(RazonVictoria.JACKE_MATE.toString(), mPartida.getRazonVictoria().toString());
    }

    private void probarJackesSinMate() throws InvocationTargetException, IllegalAccessException {

        mTablero.hacerMovimiento(mTablero.getCasilla('e', 1), mTablero.getCasilla('e', 3));
        mTablero.hacerMovimiento(mTablero.getCasilla('e', 6), mTablero.getCasilla('e', 4));
        mTablero.hacerMovimiento(mTablero.getCasilla('d', 0), mTablero.getCasilla('h', 4));
        mTablero.hacerMovimiento(mTablero.getCasilla('a', 6), mTablero.getCasilla('a', 5));
        mTablero.hacerMovimiento(mTablero.getCasilla('h', 4), mTablero.getCasilla('f', 6));

        razonVictoria = null;
        comprobarFinPartida.invoke(mGestorDeMovimientos);
        assertNull(razonVictoria);

        mTablero.hacerMovimiento(mTablero.getCasilla('e', 7), mTablero.getCasilla('f', 6));
        mTablero.hacerMovimiento(mTablero.getCasilla('f', 0), mTablero.getCasilla('c', 3));

        razonVictoria = null;
        comprobarFinPartida.invoke(mGestorDeMovimientos);
        assertNull(razonVictoria);
    }

    private void probarReyAhogado() throws InvocationTargetException, IllegalAccessException {
        mTablero.getCasilla('a', 0).setPieza(new Rey(Color.NEGRO));
        mTablero.getCasilla('c', 1).setPieza(new Reina(Color.BLANCO));

        razonVictoria = null;
        comprobarFinPartida.invoke(mGestorDeMovimientos);
        assertEquals(RazonVictoria.REY_AHOGADO.toString(), mPartida.getRazonVictoria().toString());
    }

    private void probarMaterialInsuficiente() throws InvocationTargetException, IllegalAccessException {
        mTablero.vaciarTablero();

        mTablero.getCasilla('a', 0).setPieza(new Rey(Color.BLANCO));
        mTablero.getCasilla('a', 7).setPieza(new Rey(Color.NEGRO));

        razonVictoria = null;
        comprobarFinPartida.invoke(mGestorDeMovimientos);
        assertEquals(RazonVictoria.MATERIAL_INSUFICIENTE.toString(), mPartida.getRazonVictoria().toString());
    }

    @Test
    void enrocarTest() throws InvocationTargetException, IllegalAccessException {
        crearPartidaPrueba();
        mTablero.vaciarTablero();

        testearEnroquesNormales();
        testEnroquePiezaEnMedio();
        testEnroqueBajoJaque();
        testEnroqueQuedaEnJaque();
        testearEnroquesMovidos();
    }
    private void testearEnroquesNormales() throws InvocationTargetException, IllegalAccessException {
        testEnroqueLargaBlancas();
        testEnroqueLargaNegras();
        testEnroqueCortaBlancas();
        testEnroqueCortaNegras();
    }
    private void testEnroqueLargaBlancas() throws InvocationTargetException, IllegalAccessException {
        ponerReyes();
        mTablero.getCasilla('a', 0).setPieza(new Torre(Color.BLANCO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('a', 0)));

        assertInstanceOf(Rey.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('c', 0)));
        assertInstanceOf(Torre.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('d', 0)));

        mTablero.vaciarTablero();
    }

    private void ponerReyes() {
        mTablero.getCasilla('e', 0).setPieza(new Rey(Color.BLANCO));
        mTablero.getCasilla('e', 7).setPieza(new Rey(Color.NEGRO));
    }

    private void testEnroqueLargaNegras() throws InvocationTargetException, IllegalAccessException {
        ponerReyes();
        mTablero.getCasilla('a', 7).setPieza(new Torre(Color.NEGRO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 7), mTablero.getCasilla('a', 7)));

        assertInstanceOf(Rey.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('c', 7)));
        assertInstanceOf(Torre.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('d', 7)));

        mTablero.vaciarTablero();
    }
    private void testEnroqueCortaBlancas() throws InvocationTargetException, IllegalAccessException {
        ponerReyes();
        mTablero.getCasilla('h', 0).setPieza(new Torre(Color.BLANCO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('h', 0)));

        assertInstanceOf(Rey.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('g', 0)));
        assertInstanceOf(Torre.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('f', 0)));

        mTablero.vaciarTablero();
    }
    private void testEnroqueCortaNegras() throws InvocationTargetException, IllegalAccessException {
        ponerReyes();
        mTablero.getCasilla('h', 7).setPieza(new Torre(Color.NEGRO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 7), mTablero.getCasilla('h', 7)));

        assertInstanceOf(Rey.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('g', 7)));
        assertInstanceOf(Torre.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('f', 7)));

        mTablero.vaciarTablero();
    }
    private void testEnroquePiezaEnMedio() throws InvocationTargetException, IllegalAccessException {
        ponerReyes();
        mTablero.getCasilla('a', 0).setPieza(new Torre(Color.BLANCO));
        mTablero.getCasilla('b', 0).setPieza(new Caballo(Color.BLANCO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('a', 0)));

        assertInstanceOf(PiezaNula.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('c', 0)));

        mTablero.vaciarTablero();
    }
    private void testEnroqueBajoJaque() throws InvocationTargetException, IllegalAccessException {
        ponerReyes();
        mTablero.getCasilla('a', 0).setPieza(new Torre(Color.BLANCO));
        mTablero.getCasilla('e', 7).setPieza(new Reina(Color.NEGRO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('a', 0)));

        assertInstanceOf(PiezaNula.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('c', 0)));

        mTablero.vaciarTablero();
    }
    private void testEnroqueQuedaEnJaque() throws InvocationTargetException, IllegalAccessException {
        ponerReyes();
        mTablero.getCasilla('a', 0).setPieza(new Torre(Color.BLANCO));
        mTablero.getCasilla('e', 7).setPieza(new Reina(Color.NEGRO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('a', 0)));

        assertInstanceOf(PiezaNula.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('c', 0)));

        mTablero.vaciarTablero();
    }
    private void testearEnroquesMovidos() throws InvocationTargetException, IllegalAccessException {
        testEnroqueReyMovido();
        testEnroqueTorreMovida();
    }
    private void testEnroqueReyMovido() throws InvocationTargetException, IllegalAccessException {
        mTablero.getCasilla('f', 0).setPieza(new Rey(Color.BLANCO));
        mTablero.getCasilla('a', 0).setPieza(new Torre(Color.BLANCO));
        mTablero.getCasilla('e', 7).setPieza(new PeonNegro());
        mTablero.getCasilla('e', 6).setPieza(new Rey(Color.NEGRO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('f', 0), mTablero.getCasilla('e', 0)));
        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 7), mTablero.getCasilla('e', 6)));
        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('a', 0)));

        assertInstanceOf(PiezaNula.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('c', 0)));

        mTablero.vaciarTablero();
    }
    private void testEnroqueTorreMovida() throws InvocationTargetException, IllegalAccessException {
        mTablero.getCasilla('e', 0).setPieza(new Rey(Color.BLANCO));
        mTablero.getCasilla('b', 0).setPieza(new Torre(Color.BLANCO));
        mTablero.getCasilla('e', 7).setPieza(new PeonNegro());
        mTablero.getCasilla('e', 6).setPieza(new Rey(Color.NEGRO));

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('b', 0), mTablero.getCasilla('a', 0)));
        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 7), mTablero.getCasilla('e', 6)));
        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(mTablero.getCasilla('e', 0), mTablero.getCasilla('a', 0)));

        assertInstanceOf(PiezaNula.class, getPiezaTablero.invoke(mTablero, mTablero.getCasilla('c', 0)));
    }
}