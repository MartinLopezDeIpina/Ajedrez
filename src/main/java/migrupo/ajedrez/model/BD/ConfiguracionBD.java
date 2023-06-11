package migrupo.ajedrez.model.BD;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfiguracionBD {
    private final static ConfiguracionBD instance = new ConfiguracionBD();
    public static ConfiguracionBD getInstance(){
        return instance;
    }
    private Properties propiedades = new Properties();
    private ConfiguracionBD(){
        cargarPropiedades();
    }

    private void cargarPropiedades(){
        try{
            InputStream input = new FileInputStream("migrupo/ajedrez/configBD.properties");
            propiedades.load(input);
        }catch (IOException e){
            System.out.printf("Error al cargar configuracion de base de datos");
        }
    }
    public String getUrl(){
        return propiedades.getProperty("bd.url");
    }
    public String getUsuario(){
        return propiedades.getProperty("bd.usuario");
    }
    public String getContrasena(){
        return propiedades.getProperty("bd.contrasena");
    }


}
