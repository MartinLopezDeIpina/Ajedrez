package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public class NombreInexistenteAutenticacion extends Autenticacion{
    public NombreInexistenteAutenticacion(){
        autenticado = false;

        mensajeAutenticacion = "Contraseña incorrecta";
    }
}
