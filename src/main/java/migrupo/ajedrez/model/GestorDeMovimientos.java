package migrupo.ajedrez.model;

import migrupo.ajedrez.model.BD.MovimientoDAO;
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

    public void hacerMovimiento(Movimiento movimiento){
        //todo
    }

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
