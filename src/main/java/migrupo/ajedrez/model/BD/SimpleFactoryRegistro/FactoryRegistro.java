package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

public class FactoryRegistro {
    private FactoryRegistro(){

    }
    private final static FactoryRegistro mFactoryRegistro = new FactoryRegistro();
    public static FactoryRegistro getInstance(){
        return mFactoryRegistro;
    }

    public Registro getRegistro(){

        return null;
    }
}
