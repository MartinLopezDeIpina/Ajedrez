package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public abstract class Autenticacion {

    public Autenticacion(boolean autenticado, String mensajeAutenticacion){
        this.autenticado = autenticado;
        this.mensajeAutenticacion = mensajeAutenticacion;
    }

    protected boolean autenticado;
    protected String mensajeAutenticacion;

    public boolean isAutenticado() {
        return autenticado;
    }
    public String getMensajeAutenticacion() {
        return mensajeAutenticacion;
    }


}
