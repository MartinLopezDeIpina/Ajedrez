package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartidaDAOImplTest {

    static PartidaDAOImpl mPartidaDAO;
    static ConexionBD mConexionBD;
    static Partida mPartida;
    static GestorDeMovimientos mGestorDeMovimientos;
    static Tablero mTablero;
    static UsuarioDAOImpl mUsuarioDAOImpl;

    @BeforeAll
    static void setUp() {
        mPartidaDAO = PartidaDAOImpl.getInstance();
        mConexionBD = ConexionBD.getInstance();
        mPartida = Partida.getInstance();
        mGestorDeMovimientos = GestorDeMovimientos.getInstance();
        mTablero = Tablero.getInstance();
        mUsuarioDAOImpl = UsuarioDAOImpl.getInstance();

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

    @Test
    void getPartidasTest(){

        Usuario usuario1 = new Jugador("usuarioPrueba1", "123");
        Usuario usuario2 = new Jugador("usuarioPrueba2", "123");

        mUsuarioDAOImpl.registrarUsuario("usuarioPrueba1", "123");
        mUsuarioDAOImpl.registrarUsuario("usuarioPrueba2", "123");

        int partida1 = mPartida.iniciarPartidaNueva(usuario1, usuario2);

        hacerMovimientos();

        mPartida.partidaFinalizada(RazonVictoria.JACKE_MATE, usuario1);

        int partida2 = mPartida.iniciarPartidaNueva(usuario1, usuario2);

        assertEquals(1, mPartidaDAO.getPartidasAcabadas(usuario1.getNombreValue()).size());
        assertEquals(1, mPartidaDAO.getPartidasSinAcabar(usuario1.getNombreValue()).size());

        mPartidaDAO.eliminarPartida(partida1);
        mPartidaDAO.eliminarPartida(partida2);

        mUsuarioDAOImpl.eliminarUsuario(usuario1.getNombreValue());
        mUsuarioDAOImpl.eliminarUsuario(usuario2.getNombreValue());
    }

    private void hacerMovimientos() {
        List<Movimiento> movimientosA = Arrays.asList(new Movimiento(mTablero.getCasilla('e', 1), mTablero.getCasilla('e', 3))
                ,new Movimiento(mTablero.getCasilla('e', 1), mTablero.getCasilla('e', 3))
                ,new Movimiento(mTablero.getCasilla('e', 6), mTablero.getCasilla('e', 4))
                ,new Movimiento(mTablero.getCasilla('d', 0), mTablero.getCasilla('h', 4))
                ,new Movimiento(mTablero.getCasilla('g', 7), mTablero.getCasilla('f', 2))
                ,new Movimiento(mTablero.getCasilla('h', 4), mTablero.getCasilla('g', 4))
                ,new Movimiento(mTablero.getCasilla('f', 2), mTablero.getCasilla('e', 3))
                ,new Movimiento(mTablero.getCasilla('g', 4), mTablero.getCasilla('d', 7))
                ,new Movimiento(mTablero.getCasilla('d', 1), mTablero.getCasilla('d', 2))
                ,new Movimiento(mTablero.getCasilla('e', 3), mTablero.getCasilla('c', 4))
                ,new Movimiento(mTablero.getCasilla('g', 0), mTablero.getCasilla('f', 2))
                ,new Movimiento(mTablero.getCasilla('e', 4), mTablero.getCasilla('e', 3))
                ,new Movimiento(mTablero.getCasilla('d', 2), mTablero.getCasilla('e', 3))
                ,new Movimiento(mTablero.getCasilla('c', 4), mTablero.getCasilla('e', 3))
                ,new Movimiento(mTablero.getCasilla('f', 0), mTablero.getCasilla('d', 2)),
                new Movimiento(mTablero.getCasilla('d', 6), mTablero.getCasilla('d', 4)));

        movimientosA.stream().forEach(movimiento -> mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(movimiento));
    }
}