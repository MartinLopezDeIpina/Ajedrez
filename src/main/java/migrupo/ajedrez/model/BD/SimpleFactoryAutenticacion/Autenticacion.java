package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public abstract class Autenticacion {

    protected boolean autenticado;
    protected String mensajeAutenticacion;

    public boolean isAutenticado() {
        return autenticado;
    }
    public String getMensajeAutenticacion() {
        return mensajeAutenticacion;
    }


}
