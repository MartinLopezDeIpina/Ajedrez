package migrupo.ajedrez.model.BD.SimpleFactoryRegistro;

import migrupo.ajedrez.model.BD.ConexionBD;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FactoryRegistro {
    private FactoryRegistro(){

    }
    private final static FactoryRegistro mFactoryRegistro = new FactoryRegistro();
    public static FactoryRegistro getInstance(){
        return mFactoryRegistro;
    }

    ConexionBD mConexionBD = ConexionBD.getInstance();

    public Registro getRegistro(String nombre, String contrasena){

        if(nombre.length() > 20) return new RegistroNombreLargo();

        if(contrasena.length() > 20) return new RegistroContrasenaLarga();

        if(nombre.isEmpty()) return new RegistroNombreNulo();

        if(contrasena.isEmpty()) return new RegistroContrasenaNulo();

        if(nombreEnUso(nombre)){
            return new RegistroNombreEnUso();
        }

        registrarUsuario(nombre, contrasena);

        return new RegistroCorrecto();
    }

    private boolean nombreEnUso(String nombre){
        try {

            return mConexionBD.executeQuery("select * from usuario where nombre = ?", new Object[]{nombre}).next();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void registrarUsuario(String nombre, String contrasena){
        mConexionBD.executeUpdate("insert into usuario values (?, ?)", new Object[]{nombre, contrasena});
    }
}
