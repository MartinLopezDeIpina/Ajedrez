package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

public class RegistroContrasenaNulo extends Registro{
    public RegistroContrasenaNulo(){
        super(false, "Debes especificar una contraseña");
    }
}
