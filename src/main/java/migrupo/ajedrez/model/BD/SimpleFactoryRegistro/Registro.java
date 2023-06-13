package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

public abstract class Registro {
    public Registro(boolean registrado, String mensaje){
        this.registrado = registrado;
        this.mensaje = mensaje;
    }

    private String mensaje;
    private boolean registrado;

    public String getMensaje(){
        return mensaje;
    }
    public boolean getRegistrado(){
        return registrado;
    }
}
