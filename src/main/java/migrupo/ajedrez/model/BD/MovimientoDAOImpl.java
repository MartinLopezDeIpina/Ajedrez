package migrupo.ajedrez.model.BD;

import javafx.scene.control.skin.SliderSkin;
import migrupo.ajedrez.model.Casilla;
import migrupo.ajedrez.model.Movimiento;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAOImpl implements MovimientoDAO{
    private final static MovimientoDAOImpl instance = new MovimientoDAOImpl();
    public static MovimientoDAOImpl getInstance() {
        return instance;
    }

    private ConexionBD mConexionBD;

    private MovimientoDAOImpl() {
        mConexionBD = ConexionBD.getInstance();
    }

    @Override
    public List<Movimiento> getMovimientosPartida(int identificador) {
        try {

            ResultSet rsMovimientosPartida = getRsMovimientosPartida(identificador);

            List<Movimiento> movimientos = new ArrayList<>();
            while (rsMovimientosPartida.next()){
                movimientos.add(new Movimiento(new Casilla(rsMovimientosPartida.getString("casillaOrigen").charAt(0), rsMovimientosPartida.getString("casillaOrigen").charAt(1) ),
                        new Casilla(rsMovimientosPartida.getString("casillaDestino").charAt(0), rsMovimientosPartida.getString("casillaDestino").charAt(1) )));
            }
            return movimientos;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private ResultSet getRsMovimientosPartida(int identificador){
        String queryGetMovimientosPartida = "select * from movimiento where identificador = ?";
        return mConexionBD.executeQuery(queryGetMovimientosPartida, new Object[]{identificador});
    }
}
