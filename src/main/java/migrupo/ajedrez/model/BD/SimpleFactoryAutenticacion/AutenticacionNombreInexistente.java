package migrupo.ajedrez.model.BD.SimpleFactoryAutenticacion;

public class AutenticacionNombreInexistente extends Autenticacion{
    public AutenticacionNombreInexistente(){
        super(false, "Ese nombre no está registrado máquina");
    }
}
