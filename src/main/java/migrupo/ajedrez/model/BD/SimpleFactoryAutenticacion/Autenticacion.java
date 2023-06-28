package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

import migrupo.ajedrez.model.Sesion;

public abstract class Autenticacion {
    protected boolean autenticado;
    protected String mensajeAutenticacion;

    public Autenticacion(boolean autenticado, String mensajeAutenticacion){
        this.autenticado = autenticado;
        this.mensajeAutenticacion = mensajeAutenticacion;
    }

    public boolean isAutenticado() {
        return autenticado;
    }
    public String getMensajeAutenticacion() {
        return mensajeAutenticacion;
    }


}
