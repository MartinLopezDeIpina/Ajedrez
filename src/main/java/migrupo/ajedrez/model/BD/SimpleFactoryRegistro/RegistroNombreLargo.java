package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

public class RegistroNombreLargo extends Registro{
    public RegistroNombreLargo(){
        super(false, "El nombre supera el límite de caracteres");
    }
}
