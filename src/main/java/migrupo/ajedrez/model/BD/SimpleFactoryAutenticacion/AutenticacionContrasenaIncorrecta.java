package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public class AutenticacionContrasenaIncorrecta extends Autenticacion {
    public AutenticacionContrasenaIncorrecta(){
        super(false, "La contraseña es incorrecta");
    }
}
