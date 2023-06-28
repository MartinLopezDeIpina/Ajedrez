package migrupo.ajedrez.model.BD;

import migrupo.ajedrez.AjedrezApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfiguracionBD {
    private final static ConfiguracionBD instance = new ConfiguracionBD();
    public static ConfiguracionBD getInstance(){
        return instance;
    }
    private Properties propiedades;
    private ConfiguracionBD(){
        propiedades = new Properties();

        cargarPropiedades();
    }

    private void cargarPropiedades(){
        try{
            File fiheroConfiguracion = new File(AjedrezApplication.class.getResource("configBD.properties").getFile());
            InputStream input = new FileInputStream(fiheroConfiguracion);

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
