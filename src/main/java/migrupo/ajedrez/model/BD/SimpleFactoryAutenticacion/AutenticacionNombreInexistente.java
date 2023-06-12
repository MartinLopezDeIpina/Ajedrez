package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public class AutenticacionNombreInexistente extends Autenticacion{
    public AutenticacionNombreInexistente(){
        autenticado = false;

        mensajeAutenticacion = "Contraseña incorrecta";
    }
}
