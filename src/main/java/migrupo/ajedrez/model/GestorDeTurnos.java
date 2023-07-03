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

        ponerColores(usuarioA, usuarioB);

        iniciarTurno();
    }
    private void asignarUsuarios(Usuario usuarioA, Usuario usuarioB) {
        this.usuarioA = usuarioA;
        this.usuarioB = usuarioB;
    }
    private void ponerColores(Usuario usuarioA, Usuario usuarioB) {
        if (Math.random() < 0.5) {
            usuarioA.setColor(Color.BLANCO);
            usuarioB.setColor(Color.NEGRO);
        } else {
            usuarioA.setColor(Color.BLANCO);
            usuarioB.setColor(Color.NEGRO);
        }
    }
    private void iniciarTurno(){
        if(usuarioA.getColor().equals(Color.BLANCO)){
            usuarioActual = usuarioA;
        }else{
            usuarioActual = usuarioB;
        }
    }

    public Color getColorTurno() {
        return usuarioActual.getColor();
    }

    public void pasarTurno(){
        usuarioActual = (usuarioActual == usuarioA) ? usuarioB : usuarioA;
    }
}
