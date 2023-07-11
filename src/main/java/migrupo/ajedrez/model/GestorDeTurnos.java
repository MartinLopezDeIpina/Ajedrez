package migrupo.ajedrez.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ChoiceBox;

public class GestorDeTurnos {
    private final static GestorDeTurnos instance = new GestorDeTurnos();
    public static GestorDeTurnos getInstance() {
        return instance;
    }

    private SimpleObjectProperty<Usuario> usuarioA;
    private SimpleObjectProperty<Usuario> usuarioB;
    private SimpleObjectProperty<Usuario> usuarioActual;

    private GestorDeTurnos() {
    }

    public void iniciarPartida(Usuario usuarioA, Usuario usuarioB) {
        asignarUsuarios(usuarioA, usuarioB);


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

    public Color getColorTurno() {
        return usuarioActual.getValue().getColor();
    }

    public void pasarTurno(){
        usuarioActual.set(usuarioActual.getValue().equals(usuarioA.getValue()) ? usuarioB.getValue() : usuarioA.getValue());
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
    public SimpleStringProperty getNombreUsuarioActual() {
        return usuarioActual.getValue().getNombre();
    }
}
