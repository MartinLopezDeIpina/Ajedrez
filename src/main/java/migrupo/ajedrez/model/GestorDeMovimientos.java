package migrupo.ajedrez.model;

import migrupo.ajedrez.model.BD.MovimientoDAOImpl;

import java.util.List;

public class GestorDeMovimientos {
    private final static GestorDeMovimientos instance = new GestorDeMovimientos();
    public static GestorDeMovimientos getInstance() {
        return instance;
    }

    private MovimientoDAOImpl mMovimientoDAO;
    private Tablero mTablero;
    private GestorDeTurnos mGestorDeTurnos;

    private GestorDeMovimientos() {
        mMovimientoDAO = MovimientoDAOImpl.getInstance();
        mTablero = Tablero.getInstance();
        mGestorDeTurnos = GestorDeTurnos.getInstance();
    }

    public void hacerMovimientoYPasarTurno(Movimiento movimiento){
        if(movimientoPosible(movimiento)){
            mTablero.hacerMovimiento(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());
        }

        mGestorDeTurnos.pasarTurno();
    }
    private boolean movimientoPosible(Movimiento movimiento) {

        return casillaOrigenNoEstaVacia(movimiento.getCasillaOrigen()) &&
                leTocaJugarAlJugadorCorrecto(movimiento.getCasillaOrigen()) &&
                casillaDestinoNoTienePiezaDelMismoColor(movimiento.getCasillaDestino()) &&
                piezaPuedeMoverseACasillaDestino(movimiento) &&
                noHayPiezasEntreCasillaOrigenYCasillaDestino(movimiento) &&
                reyNoQuedaEnJaque(movimiento);
    }
    private boolean casillaOrigenNoEstaVacia(Casilla casilla) {return !mTablero.casillaVacia(casilla);}
    private boolean leTocaJugarAlJugadorCorrecto(Casilla casilla) {return mTablero.getColorPiezaEnCasilla(casilla) == mGestorDeTurnos.getColorTurno();}
    private boolean casillaDestinoNoTienePiezaDelMismoColor(Casilla casilla) {return mTablero.getColorPiezaEnCasilla(casilla) != mGestorDeTurnos.getColorTurno();}
    private boolean piezaPuedeMoverseACasillaDestino(Movimiento movimiento) {return movimiento.piezaPuedeMoverseACasillaDestino();}
    private boolean noHayPiezasEntreCasillaOrigenYCasillaDestino(Movimiento movimiento) {
        return !mTablero.hayPiezasEntreCasillaOrigenYCasillaDestino(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());}
    private boolean reyNoQuedaEnJaque(Movimiento movimiento) {
        return !mTablero.reyQuedaEnJaque(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());
    }

    public void setPartida(int identificador, Usuario usuarioB, Usuario usuarioN) {
        ponerPosicionesIniciales();

        mGestorDeTurnos.iniciarPartida(usuarioB, usuarioN);

        ejecutarMovimientosGuardados(identificador);
    }
    private void ponerPosicionesIniciales() {
        mTablero.reiniciarTablero();
    }

    private void ejecutarMovimientosGuardados(int identificador) {

        List<Movimiento> movimientos = mMovimientoDAO.getMovimientosPartida(identificador);

        movimientos.stream().forEach(movimiento -> cargarPiezasEnMovimiento(movimiento));

        movimientos.stream().forEach(movimiento -> hacerMovimientoYPasarTurno(movimiento));
    }

    private void cargarPiezasEnMovimiento(Movimiento movimiento) {
        movimiento.actualizarPiezas(mTablero.getPiezas(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino()));
    }


}
