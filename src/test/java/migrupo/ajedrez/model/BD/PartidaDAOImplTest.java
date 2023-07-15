package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.Jugador;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PartidaDAOImplTest {

    static PartidaDAOImpl mPartidaDAO;
    static ConexionBD mConexionBD;

    @BeforeAll
    static void setUp() {
        mPartidaDAO = PartidaDAOImpl.getInstance();
        mConexionBD = ConexionBD.getInstance();

        mConexionBD.establecerConexion();
    }
    @AfterAll
    static void tearDown() {
        mConexionBD.cerrarConexion();
    }

    @Test
    void registrarPartida() {

        int idPartida = mPartidaDAO.registrarPartida(new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

        int maximoIdentificador = getMaximoIdentificador();

        assertEquals(maximoIdentificador, idPartida);

        mPartidaDAO.eliminarPartida(idPartida);
    }

    private int getMaximoIdentificador() {
        try {

            String queryGetMaximoIdentificador = "select max(identificador) from partida";
            ResultSet rs = mConexionBD.executeQuery(queryGetMaximoIdentificador, new Object[]{});
            rs.next();
            return rs.getInt("max(identificador)");

        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void eliminarPartida() {

        int idPartida = mPartidaDAO.registrarPartida(new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

        mPartidaDAO.eliminarPartida(idPartida);

        assertEquals(idPartida-1, getMaximoIdentificador());
    }

    @Test
    void finalizarPartidaTest(){
        int idPartida = mPartidaDAO.registrarPartida(new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

        assertEquals(0, mPartidaDAO.getEstadoPartida(idPartida));

        mPartidaDAO.finalizarPartida(idPartida);

        assertEquals(1, mPartidaDAO.getEstadoPartida(idPartida));

        mPartidaDAO.eliminarPartida(idPartida);
    }

    @Test
    void getEstadoPartidaTest(){
        int idPartida = mPartidaDAO.registrarPartida(new Jugador("pepe", "123"), new Jugador("pepe2", "123"));

        assertEquals(0, mPartidaDAO.getEstadoPartida(idPartida));

        mPartidaDAO.finalizarPartida(idPartida);

        assertEquals(1, mPartidaDAO.getEstadoPartida(idPartida));

        mPartidaDAO.eliminarPartida(idPartida);
    }
}