package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

public class RegistroNombreEnUso extends Registro{
    public RegistroNombreEnUso(){
        super(false, "Este nombre ya ha sido escogido");
    }
}
