package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Movimiento;

import java.util.List;

public interface MovimientoDAO {

    List<Movimiento> getMovimientosPartida(int identificador);
}
