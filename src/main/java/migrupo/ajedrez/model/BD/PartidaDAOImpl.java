package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Movimiento;
import migrupo.ajedrez.model.Partida;
import migrupo.ajedrez.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PartidaDAOImpl implements PartidaDAO{
    private final static PartidaDAOImpl mPartidaDAOImpl = new PartidaDAOImpl();
    public static PartidaDAOImpl getInstance(){
        return mPartidaDAOImpl;
    }

    ConexionBD mConexionBD;

    private PartidaDAOImpl(){
        mConexionBD = ConexionBD.getInstance();
    }

    @Override
    public int registrarPartida(Usuario dueno, Usuario contrinctante) {
        try {

            int identificador = getIdentificadorCorrespondiente();

            registrarPartida(identificador, dueno, contrinctante);

            return identificador;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private int getIdentificadorCorrespondiente()throws SQLException{
        String queryGetIdentificador = "select max(identificador) from partida";

        ResultSet rs = mConexionBD.executeQuery(queryGetIdentificador, new Object[]{});
        rs.next();

        return rs.getInt("max(identificador)") + 1;
    }
    private void registrarPartida(int identificador, Usuario dueno, Usuario contrinctante) throws SQLException{
        String queryRegistrarPartida = "insert into partida values (?, ?, ?, ?)";
        mConexionBD.executeUpdate(queryRegistrarPartida, new Object[]{identificador, dueno.getNombre().get(), contrinctante.getNombre().get(), 0});
    }

    @Override
    public void eliminarPartida(int identificador) {

        String queryEliminarMovimientos = "delete from movimientoPartida where idPartida = ?";
        mConexionBD.executeUpdate(queryEliminarMovimientos, new Object[]{identificador});

        String queryEliminarPartida = "delete from partida where identificador = ?";
        mConexionBD.executeUpdate(queryEliminarPartida, new Object[]{identificador});

    }

    public void finalizarPartida(int identificador) {
        String queryFinalizarPartida = "update partida set acabado = 1 where identificador = ?";
        mConexionBD.executeUpdate(queryFinalizarPartida, new Object[]{identificador});
    }

    public int getEstadoPartida(int idPartida) {
        String queryGetEstadoPartida = "select acabado from partida where identificador = ?";
        ResultSet rs = mConexionBD.executeQuery(queryGetEstadoPartida, new Object[]{idPartida});

        try {
            rs.next();
            return rs.getInt("acabado");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
