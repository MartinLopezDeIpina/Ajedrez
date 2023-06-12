package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public class ContrasenaIncorrectaAutenticacion extends Autenticacion {
    public ContrasenaIncorrectaAutenticacion(){
        autenticado = false;

        mensajeAutenticacion = "La contraseña es incorrecta";
    }
}
