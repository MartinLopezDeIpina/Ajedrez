package migrupo.ajedrez.model;

public class GestorDeTurnos {
    private GestorDeTurnos() {
    }
    private final static GestorDeTurnos instance = new GestorDeTurnos();
    public static GestorDeTurnos getInstance() {
        return instance;
    }
}
