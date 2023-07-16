package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Jugador;
import migrupo.ajedrez.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    // todo: hacer tests de esto
    // todo: hay bug de que algunos movimientos al final nose cargan
    public List<String[]> getPartidasSinAcabar(String nombreValue) {
        return getPartidas(nombreValue, 0);
    }
    public List<String[]> getPartidasAcabadas(String nombreValue) {
        return getPartidas(nombreValue, 1);
    }
    private List<String[]> getPartidas(String nombreValue, int acabadas) {
        String queryGetPartidas = "select * from partida where (nombreJugadorA like ? or nombreJugadorB like ?) and acabado = ?";
        ResultSet rs = mConexionBD.executeQuery(queryGetPartidas, new Object[]{nombreValue, nombreValue, acabadas});

        return getPartidasFromResultSet(rs, nombreValue);
    }

    private List<String[]> getPartidasFromResultSet(ResultSet rs, String nombreValue) {
        List<String[]> partidas = new ArrayList<>();

        try {

            while (rs.next()) {

                partidas.add(getSiguientePartida(rs, nombreValue));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return partidas;
    }

    private String[] getSiguientePartida(ResultSet rs, String nombreValue) throws SQLException {

        String[] partida = new String[2];

        partida[0] = rs.getString("identificador");

        String nombreJugadorA = rs.getString("nombreJugadorA");
        String nombreJugadorB = rs.getString("nombreJugadorB");

        partida[1] = nombreJugadorA.equals(nombreValue) ? nombreJugadorB : nombreJugadorA;

        return partida;
    }

    public String[] getUsuariosPartida(int idPartida) {

        String queryGetUsuariosPartida = "select nombreJugadorA, nombreJugadorB from partida where identificador = ?";
        ResultSet rs = mConexionBD.executeQuery(queryGetUsuariosPartida, new Object[]{idPartida});

        try {
            rs.next();

            String nombreJugadorA = rs.getString("nombreJugadorA");
            String nombreJugadorB = rs.getString("nombreJugadorB");

            return new String[]{nombreJugadorA, nombreJugadorB};

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
