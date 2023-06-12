package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public class AutenticacionCorrecto extends Autenticacion{
    public AutenticacionCorrecto(){
        autenticado = true;

        mensajeAutenticacion = "Contraseña correcta";
    }
}
