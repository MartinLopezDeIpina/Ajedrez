package migrupo.ajedrez.model;

import migrupo.ajedrez.model.BD.MovimientoDAOImpl;

import java.util.List;

public class GestorDeMovimientos {
    private GestorDeMovimientos() {
    }
    private final static GestorDeMovimientos instance = new GestorDeMovimientos();
    public static GestorDeMovimientos getInstance() {
        return instance;
    }

    private MovimientoDAOImpl movimientoDAO = MovimientoDAOImpl.getInstance();
    private Tablero mTablero = Tablero.getInstance();
    private GestorDeTurnos mGestorDeTurnos = GestorDeTurnos.getInstance();

    public void hacerMovimiento(Movimiento movimiento){
        if(movimientoPosible(movimiento)){
            mTablero.hacerMovimiento(movimiento);
        }
    }
    private boolean movimientoPosible(Movimiento movimiento) {

        return casillaOrigenNoEstaVacia(movimiento.getCasillaOrigen()) &&
                casillaOrigenTienePiezaCorrecta(movimiento.getCasillaOrigen()) &&
                leTocaJugarAlJugadorCorrecto(movimiento.getCasillaOrigen()) &&
                casillaDestinoNoTienePiezaDelMismoColor(movimiento.getCasillaDestino()) &&
                piezaPuedeMoverseACasillaDestino(movimiento) &&
                noHayPiezasEntreCasillaOrigenYCasillaDestino() &&
                reyNoQuedaEnJaque();
    }
    private boolean casillaOrigenNoEstaVacia(Casilla casilla) {return !mTablero.casillaVacia(casilla);}
    private boolean casillaOrigenTienePiezaCorrecta(Casilla casilla) {return mTablero.getColorPiezaEnCasilla(casilla) == mGestorDeTurnos.getColorTurno();}
    private boolean leTocaJugarAlJugadorCorrecto(Casilla casilla) {return mTablero.getColorPiezaEnCasilla(casilla) == mGestorDeTurnos.getColorTurno();}
    private boolean casillaDestinoNoTienePiezaDelMismoColor(Casilla casilla) {return mTablero.getColorPiezaEnCasilla(casilla) != mGestorDeTurnos.getColorTurno();}
    private boolean piezaPuedeMoverseACasillaDestino(Movimiento movimiento) {return movimiento.piezaPuedeMoverseACasillaDestino();}

    public void setPartida(int identificador) {
        ponerPosicionesIniciales();

        ejecutarMovimientosGuardados(identificador);
    }
    private void ponerPosicionesIniciales() {
        mTablero.ponerPosicionesIniciales();
    }

    private void ejecutarMovimientosGuardados(int identificador) {

        List<Movimiento> movimientos = movimientoDAO.getMovimientosPartida(identificador);

        movimientos.stream().forEach(movimiento -> hacerMovimiento(movimiento));
    }


}
