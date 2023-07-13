package migrupo.ajedrez.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class GestorDeTurnos {
    private final static GestorDeTurnos instance = new GestorDeTurnos();
    public static GestorDeTurnos getInstance() {
        return instance;
    }

    private Partida mPartida;

    private SimpleObjectProperty<Usuario> usuarioA;
    private SimpleObjectProperty<Usuario> usuarioB;
    private SimpleObjectProperty<Usuario> usuarioActual;

    private SimpleBooleanProperty acabado;


    private GestorDeTurnos() {

    }

    public void iniciarPartida(Usuario usuarioA, Usuario usuarioB) {
        mPartida = Partida.getInstance();

        asignarUsuarios(usuarioA, usuarioB);

        quitarAcabado();

        iniciarTurno();
    }
    private void asignarUsuarios(Usuario usuarioA, Usuario usuarioB) {
        this.usuarioA = new SimpleObjectProperty<>(usuarioA);
        this.usuarioB = new SimpleObjectProperty<>(usuarioB);
        this.usuarioActual = new SimpleObjectProperty<>();
    }
    private void iniciarTurno(){
        usuarioActual.set(usuarioA.getValue().getColor().equals(Color.BLANCO) ? usuarioA.getValue() : usuarioB.getValue());
    }
    private void quitarAcabado() {
        acabado = new SimpleBooleanProperty(false);
    }

    public Color getColorTurno() {
        return usuarioActual.getValue().getColor();
    }

    public void pasarTurno(){
        if(!acabado.getValue()){
            usuarioActual.set(usuarioActual.getValue().getColor().equals(Color.BLANCO) ? usuarioB.getValue() : usuarioA.getValue());
        }
    }

    public SimpleStringProperty getNomberUsuarioBlanco(){
        return usuarioA.getValue().getColor().equals(Color.BLANCO) ? usuarioA.getValue().getNombre() : usuarioB.getValue().getNombre();
    }
    public SimpleStringProperty getNombreUsuarioNegro(){
        return usuarioA.getValue().getColor().equals(Color.BLANCO) ? usuarioB.getValue().getNombre() : usuarioA.getValue().getNombre();
    }

    public SimpleObjectProperty <Usuario> getUsuarioActual() {
        return usuarioActual;
    }
    public Usuario getUsuarioActualValue() {
        return usuarioActual.getValue();
    }
    public SimpleStringProperty getNombreUsuarioActual() {
        return usuarioActual.getValue().getNombre();
    }

    public void setJackeMateYGanador() {
        mPartida.partidaFinalizada(RazonVictoria.JACKE_MATE, usuarioActual.getValue());

        finalizarPartida();
    }
    private void finalizarPartida() {
        acabado.set(true);
    }

    public SimpleBooleanProperty getAcabado() {
        return acabado;
    }

}
