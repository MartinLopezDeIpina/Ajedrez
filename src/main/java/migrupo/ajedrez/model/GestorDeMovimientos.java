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


    private Movimiento[] movimientosParaVerPartida;

    private Casilla casillaSeleccionada;
    private GestorDeMovimientos() {
        mMovimientoDAO = MovimientoDAOImpl.getInstance();
        mTablero = Tablero.getInstance();
        mGestorDeTurnos = GestorDeTurnos.getInstance();
    }

    public void hacerMovimientoPasarTurnoYGuardarMovimiento(Movimiento movimiento){
        boolean movimientoCorredto = hacerMovimiento(movimiento);

        if(movimientoCorredto){
            guardarMovimientoYPasarDeTurno(movimiento);
        }

    }

    public boolean hacerMovimiento(Movimiento movimiento){

        boolean movimientoCorrecto = false;

        if(esEnroqueValido(movimiento)) {

            enrocar(movimiento);
            movimientoCorrecto = true;

        }else if(esCoronacion(movimiento) && movimientoPosible(movimiento)) {

            coronar(movimiento);
            movimientoCorrecto = true;

        }else if(movimientoPosible(movimiento)){

            mTablero.hacerMovimiento(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());
            movimientoCorrecto = true;

        }
        return movimientoCorrecto;
    }

    private void guardarMovimientoYPasarDeTurno(Movimiento movimiento){
        comprobarFinPartida();

        guardarMovimiento(movimiento);

        mGestorDeTurnos.pasarTurno();
    }

    private void guardarMovimiento(Movimiento movimiento) {
        mMovimientoDAO.guardarMovimiento(mGestorDeTurnos.getIdentificadorPartida(), movimiento);
    }

    public void hacerMovimientoYPasarTurnoSinGuardar(Movimiento movimiento){
        boolean movimientoCorredto = hacerMovimiento(movimiento);

        if(movimientoCorredto){
            comprobarFinPartida();

            mGestorDeTurnos.pasarTurno();
        }
    }


    private boolean movimientoPosibleIndependientementeDelTurno(Movimiento movimiento) {
        return casillaOrigenNoEstaVacia(movimiento.getCasillaOrigen()) &&
                casillaDestinoNoTienePiezaDelMismoColor(movimiento) &&
                piezaPuedeMoverseACasillaDestino(movimiento) &&
                noHayPiezasEntreCasillaOrigenYCasillaDestino(movimiento) &&
                reyNoQuedaEnJaque(movimiento);
    }

    private boolean movimientoPosible(Movimiento movimiento) {

        return movimientoPosibleIndependientementeDelTurno(movimiento) &&
                leTocaJugarAlJugadorCorrecto(movimiento.getCasillaOrigen());

    }

    private boolean movimientoPosibleParaEnrocar(Movimiento movimiento){
        return casillaOrigenNoEstaVacia(movimiento.getCasillaOrigen()) &&
                leTocaJugarAlJugadorCorrecto(movimiento.getCasillaOrigen()) &&
                noHayPiezasEntreCasillaOrigenYCasillaDestino(movimiento) &&
                reyNoQuedaEnJaqueAlEnrocar(movimiento) &&
                !mTablero.algunaPiezaAmenazaAlRey(mGestorDeTurnos.getColorTurno());
    }
    private boolean casillaOrigenNoEstaVacia(Casilla casilla) {return !mTablero.casillaVacia(casilla);}
    private boolean leTocaJugarAlJugadorCorrecto(Casilla casilla) {return mTablero.getColorPiezaEnCasilla(casilla) == mGestorDeTurnos.getColorTurno();}
    private boolean casillaDestinoNoTienePiezaDelMismoColor(Movimiento movimiento) {return mTablero.getColorPiezaEnCasilla(movimiento.getCasillaOrigen()) != mTablero.getColorPiezaEnCasilla(movimiento.getCasillaDestino());}
    private boolean piezaPuedeMoverseACasillaDestino(Movimiento movimiento) {return movimiento.piezaPuedeMoverseACasillaDestino();}
    private boolean noHayPiezasEntreCasillaOrigenYCasillaDestino(Movimiento movimiento) {
        return !mTablero.hayPiezasEntreCasillaOrigenYCasillaDestino(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());}
    private boolean reyNoQuedaEnJaque(Movimiento movimiento) {
        return !mTablero.reyQuedaEnJaque(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());
    }
    private boolean reyNoQuedaEnJaqueAlEnrocar(Movimiento movimiento) {
        return !mTablero.reyQuedaEnJaqueAlEnrocar(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());
    }

    private void coronar(Movimiento movimiento) {
        mTablero.coronar(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());
    }
    private boolean esCoronacion(Movimiento movimiento) {
        return (movimiento.getCasillaOrigen().getPiezaValue() instanceof PeonBlanco && movimiento.getNumDestino() == 7)
                || (movimiento.getCasillaOrigen().getPiezaValue() instanceof PeonNegro  && movimiento.getNumDestino() == 0);
    }

    private void enrocar(Movimiento movimiento) {
        mTablero.enrocar(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino());
    }

    private boolean esEnroqueValido(Movimiento movimiento) {
        return mTablero.esEnroqueValido(movimiento.getCasillaOrigen(), movimiento.getCasillaDestino()) && movimientoPosibleParaEnrocar(movimiento);
    }

    private void comprobarFinPartida() {
        Color colorPosibleMate = mGestorDeTurnos.getColorTurno() == Color.NEGRO ? Color.BLANCO : Color.NEGRO;

        List<Movimiento> movimientosPosibles = getMovimientosPosibles(colorPosibleMate);
        boolean sinMovimientos = movimientosPosibles.size() == 0;
        
        if(sinMovimientos){
           noHayMovimientosPosibles(colorPosibleMate);
        }

        comprobarMaterialInsuficiente();
    }

    private List<Movimiento> getMovimientosPosibles(Color colorRival) {
        return mTablero.getMovimientosPosibles(colorRival).stream()
                .map(movimiento -> new Movimiento(movimiento[0], movimiento[1]))
                .filter(movimiento -> movimientoPosibleIndependientementeDelTurno(movimiento))
                .toList();
    }
    private void noHayMovimientosPosibles(Color colorPosibleMate) {
        if(mTablero.algunaPiezaAmenazaAlRey(colorPosibleMate)) mGestorDeTurnos.setFinalizarPartida(RazonVictoria.JACKE_MATE);

        else mGestorDeTurnos.setFinalizarPartida(RazonVictoria.REY_AHOGADO);
    }

    private void comprobarMaterialInsuficiente() {
        if(mTablero.materialInsuficiente()) mGestorDeTurnos.setFinalizarPartida(RazonVictoria.MATERIAL_INSUFICIENTE);
    }


    public void setPartidaParaJugar(int identificador, Usuario usuarioB, Usuario usuarioN) {
        setPartidaParaVer(identificador, usuarioB, usuarioN);

        ejecutarMovimientosGuardados(identificador);
    }

    public void setPartidaParaVer(int identificador, Usuario usuarioB, Usuario usuarioN){
        ponerPosicionesIniciales();

        mGestorDeTurnos.iniciarPartida(usuarioB, usuarioN);
    }

    private void ponerPosicionesIniciales() {
        mTablero.reiniciarTablero();
    }

    private void ejecutarMovimientosGuardados(int identificador) {

        List<Movimiento> movimientos = mMovimientoDAO.getMovimientosPartida(identificador);

        movimientos.stream().forEach(movimiento -> {
            cargarPiezasEnMovimiento(movimiento);
            hacerMovimientoYPasarTurnoSinGuardar(movimiento);
        });
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
        hacerMovimientoPasarTurnoYGuardarMovimiento(new Movimiento(casillaSeleccionada, casilla));
        desseleccionar();
    }

    public void desseleccionar(){
        if(hayCasillaSeleccionada()){
            casillaSeleccionada.deseleccionarCasilla();
            casillaSeleccionada = null;
        }
    }


}
