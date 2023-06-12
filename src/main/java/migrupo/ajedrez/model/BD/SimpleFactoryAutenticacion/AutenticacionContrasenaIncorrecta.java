package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public class AutenticacionContrasenaIncorrecta extends Autenticacion {
    public AutenticacionContrasenaIncorrecta(){
        autenticado = false;

        mensajeAutenticacion = "La contraseña es incorrecta";
    }
}
