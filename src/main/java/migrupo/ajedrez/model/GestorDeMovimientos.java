package migrupo.ajedrez.model;

public class GestorDeMovimientos {
    private GestorDeMovimientos() {
    }
    private final static GestorDeMovimientos instance = new GestorDeMovimientos();
    public static GestorDeMovimientos getInstance() {
        return instance;
    }
}
