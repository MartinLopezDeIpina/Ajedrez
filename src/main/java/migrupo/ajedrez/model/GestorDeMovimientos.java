package migrupo.ajedrez.model;

import migrupo.ajedrez.model.BD.MovimientoDAOImpl;
import migrupo.ajedrez.model.Piezas.PeonBlanco;
import migrupo.ajedrez.model.Piezas.PeonNegro;
import migrupo.ajedrez.model.StateCasilla.Casilla;

import java.util.List;

public class GestorDeMovimientos {
    private final static GestorDeMovimientos instance = new GestorDeMovimientos();
    public static GestorDeMovimientos getInstance() {
        return instance;
    }

    private MovimientoDAOImpl mMovimientoDAO;
    private Tablero mTablero;
    private GestorDeTurnos mGestorDeTurnos;

    private Casilla casillaSeleccionada;
    private GestorDeMovimientos() {
        mMovimientoDAO = MovimientoDAOImpl.getInstance();
        mTablero = Tablero.getInstance();
        mGestorDeTurnos = GestorDeTurnos.getInstance();
    }

    public void hacerMovimientoYPasarTurno(Movimiento movimiento){
        if(movimientoPosible(movimiento) && !coronarSiPosible(movimiento)){

            mTablero.hacerMovimiento(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());

            comprobarJackeMate(movimiento);

            mGestorDeTurnos.pasarTurno();
        }
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

    private boolean coronarSiPosible(Movimiento movimiento) {
        if(esCoronacion(movimiento)){
            mTablero.coronar(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());
            mGestorDeTurnos.pasarTurno();
            return true;
        }
        return false;
    }
    private boolean esCoronacion(Movimiento movimiento) {
        return (movimiento.getCasillaOrigen().getPiezaValue() instanceof PeonBlanco && movimiento.getNumDestino() == 7)
                || (movimiento.getCasillaOrigen().getPiezaValue() instanceof PeonNegro  && movimiento.getNumDestino() == 0);
    }

    // todo: test de esto
    private void comprobarJackeMate(Movimiento movimiento){
        if(esJackeMate()){
            mGestorDeTurnos.setJackeMate();
        }
    }
    private boolean esJackeMate() {
        Color colorPosibleMate = mGestorDeTurnos.getColorTurno() == Color.BLANCO ? Color.NEGRO : Color.BLANCO;

        if(mTablero.algunaPiezaAmenazaAlRey(colorPosibleMate)){

            List<Movimiento> movimientosPosibles = getMovimientosPosibles(colorPosibleMate);

            // todo: no da lo esperado, hacer los tests y comprobar que es lo que falla
            return movimientosPosibles.size() == 0;
            //return algunMovimientoProtegeAlRey(movimientosPosibles);
        }
        return false;
    }

    private List<Movimiento> getMovimientosPosibles(Color colorRival) {
        return mTablero.getMovimientosPosibles(colorRival).stream()
                .map(movimiento -> new Movimiento(movimiento[0], movimiento[1]))
                .filter(movimiento -> movimientoPosible(movimiento))
                .toList();
    }

    private boolean algunMovimientoProtegeAlRey(List<Movimiento> movimientosPosibles) {
        return movimientosPosibles.stream().anyMatch(
                movimientoPosible -> !mTablero.reyQuedaEnJaque(movimientoPosible.getCasillaOrigen(), movimientoPosible.getCasillaDestino()));
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

    public void casillaSeleccionada(Casilla casilla) {

        if(!hayCasillaSeleccionada()) {
            seleccionarCasilla(casilla);
        }else {
            seleccionarSegundaCasilla(casilla);
        }
    }

    private boolean hayCasillaSeleccionada() {
        return casillaSeleccionada != null;
    }

    private void seleccionarCasilla(Casilla casilla) {
        casillaSeleccionada = casilla;
        casilla.seleccionarCasilla();
    }
    private void seleccionarSegundaCasilla(Casilla casilla) {
        hacerMovimientoYPasarTurno(new Movimiento(casillaSeleccionada, casilla));
        desseleccionar();
    }

    public void desseleccionar(){
        if(hayCasillaSeleccionada()){
            casillaSeleccionada.deseleccionarCasilla();
            casillaSeleccionada = null;
        }
    }

}
