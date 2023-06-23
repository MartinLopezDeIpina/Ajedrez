package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Movimiento;
import migrupo.ajedrez.model.Partida;
import migrupo.ajedrez.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PartidaDAOImpl implements PartidaDAO{
    private PartidaDAOImpl(){

    }
    private final static PartidaDAOImpl mPartidaDAOImpl = new PartidaDAOImpl();
    public static PartidaDAOImpl getInstance(){
        return mPartidaDAOImpl;
    }

    ConexionBD mConexionBD = ConexionBD.getInstance();
    Partida mPartida = Partida.getInstance();


    @Override
    public int registrarPartida(Usuario dueno, Usuario contrinctante) {
        try {

            int identificador = getIdentificadorCorrespondiente(dueno, contrinctante);

            registrarPartida(identificador, dueno, contrinctante);

            return identificador;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private int getIdentificadorCorrespondiente(Usuario dueno, Usuario contrinctante)throws SQLException{
        String queryGetIdentificador = "select max(identificador) from partida where nombreJugadorA = ?";
        return mConexionBD.executeQuery(queryGetIdentificador, new Object[]{dueno.getNombre()}).getInt("identificador") + 1;
    }
    private void registrarPartida(int identificador, Usuario dueno, Usuario contrinctante) throws SQLException{
        String queryRegistrarPartida = "insert into partida values (?, ?, ?)";
        mConexionBD.executeUpdate(queryRegistrarPartida, new Object[]{identificador, dueno.getNombre(), contrinctante.getNombre()});
    }

}
