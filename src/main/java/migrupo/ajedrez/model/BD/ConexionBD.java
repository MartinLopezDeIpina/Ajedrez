package migrupo.ajedrez.model.BD;

import java.sql.*;


public class ConexionBD {
    private ConexionBD(){
        establecerConexion();
    }
    private final static ConexionBD instance = new ConexionBD();
    public static ConexionBD getInstance(){
        return instance;
    }

    private ConfiguracionBD mConfiguracionBD = ConfiguracionBD.getInstance();
    final String url = mConfiguracionBD.getUrl();
    final String usuario = mConfiguracionBD.getUsuario();
    final String contrasena = mConfiguracionBD.getContrasena();

    Connection con = null;

    private void establecerConexion(){
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(String.format("%s?user=%s&password=%s", url, usuario, contrasena));

        }catch (ClassNotFoundException e){
            System.out.println("Driver pel");
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try{
                con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
