package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public class AutenticacionNombreInexistente extends Autenticacion{
    public AutenticacionNombreInexistente(){
        super(false, "Contraseña incorrecta");
    }
}
