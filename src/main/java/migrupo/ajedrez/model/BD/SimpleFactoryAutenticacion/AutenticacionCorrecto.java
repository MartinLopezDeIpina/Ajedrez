package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

import migrupo.ajedrez.model.Sesion;

public class AutenticacionCorrecto extends Autenticacion{

    public AutenticacionCorrecto(){
        super(true, "Contraseña correcta");
    }
}
