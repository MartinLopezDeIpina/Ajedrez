package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

public class RegistroContrasenaLarga extends Registro{
    public RegistroContrasenaLarga(){
        super(false, "La contrasena supera el número de caracteres");
    }
}
