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

    private Connection con = null;


    private void establecerConexion(){
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(String.format("%s?user=%s&password=%s", url, usuario, contrasena));

        }catch (ClassNotFoundException e){
            System.out.println("Driver pel");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void cerrarConexion(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement st = con.prepareStatement(query);
        return st.executeQuery();
    }

    public void executeUpdate(String update)  {
        try {
            PreparedStatement st = con.prepareStatement(update);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
