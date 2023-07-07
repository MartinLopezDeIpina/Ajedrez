package migrupo.ajedrez.model;

public class GestorDeTurnos {
    private final static GestorDeTurnos instance = new GestorDeTurnos();
    public static GestorDeTurnos getInstance() {
        return instance;
    }

    private Usuario usuarioA;
    private Usuario usuarioB;
    private Usuario usuarioActual;

    private GestorDeTurnos() {
    }

    public void iniciarPartida(Usuario usuarioA, Usuario usuarioB) {
        asignarUsuarios(usuarioA, usuarioB);


        iniciarTurno();
    }
    private void asignarUsuarios(Usuario usuarioA, Usuario usuarioB) {
        this.usuarioA = usuarioA;
        this.usuarioB = usuarioB;
    }

    private void iniciarTurno(){
        usuarioActual = usuarioA.getColor().equals(Color.BLANCO) ? usuarioA : usuarioB;
    }

    public Color getColorTurno() {
        return usuarioActual.getColor();
    }

    public void pasarTurno(){
        usuarioActual = (usuarioActual == usuarioA) ? usuarioB : usuarioA;
    }
}
