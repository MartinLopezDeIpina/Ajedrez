package migrupo.ajedrez.view;

import javafx.scene.effect.Effect;
import migrupo.ajedrez.model.BD.ConexionBD;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EfectosDAOImpl implements EfectosDAO{

    private static final EfectosDAOImpl mEfectosDAOImpl = new EfectosDAOImpl();

    ConexionBD mConexionBD;

    private EfectosDAOImpl(){
        mConexionBD = ConexionBD.getInstance();
    }

    public static EfectosDAOImpl getInstance(){
        return mEfectosDAOImpl;
    }

    @Override
    public Effect getEfecto(TipoEfecto nombreEfecto) {
        String[] cuerpoEfecto = getCuerpoEfecto(nombreEfecto);

        return convertirEfecto(cuerpoEfecto);
    }
    private String[] getCuerpoEfecto(TipoEfecto nombreEfecto) {
        String query = String.format("SELECT efecto, tipoefecto FROM efectos WHERE nombreEfecto = ?");

        try{

            ResultSet rs = mConexionBD.executeQuery(query, new Object[]{nombreEfecto.toString()});
            rs.next();
            return new String[] {rs.getString("efecto"), rs.getString("tipoefecto")};

        }catch (SQLException e){
            System.out.println(String.format("Error al obtener el efecto %s", nombreEfecto));
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    private Effect convertirEfecto(String[] cuerpoEfecto) {
        String tipoEfecto = cuerpoEfecto[1];
        String efecto = cuerpoEfecto[0];

        switch (tipoEfecto){
            case "ColorAdjust":
                return convertirColorAdjust(efecto);
            case "DropShadow":
                return convertirDropShadow(efecto);
            case "GaussianBlur":
                return convertirGaussianBlur(efecto);
            case "Glow":
                return convertirGlow(efecto);
            case "InnerShadow":
                return convertirInnerShadow(efecto);
            case "MotionBlur":
                return convertirMotionBlur(efecto);
            case "PerspectiveTransform":
                return convertirPerspectiveTransform(efecto);
            case "Reflection":
                return convertirReflection(efecto);
            case "SepiaTone":
                return convertirSepiaTone(efecto);
            default:
                throw new RuntimeException();
        }
    }
    private Effect convertirSepiaTone(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.SepiaTone(Double.parseDouble(parametros[0]));
    }

    private Effect convertirReflection(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.Reflection(Double.parseDouble(parametros[0]), Double.parseDouble(parametros[1]), Double.parseDouble(parametros[2]), Double.parseDouble(parametros[3]));
    }

    private Effect convertirPerspectiveTransform(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.PerspectiveTransform(Double.parseDouble(parametros[0]), Double.parseDouble(parametros[1]), Double.parseDouble(parametros[2]), Double.parseDouble(parametros[3]), Double.parseDouble(parametros[4]), Double.parseDouble(parametros[5]), Double.parseDouble(parametros[6]), Double.parseDouble(parametros[7]));
    }

    private Effect convertirMotionBlur(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.MotionBlur(Double.parseDouble(parametros[0]), Double.parseDouble(parametros[1]));
    }

    private Effect convertirInnerShadow(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.InnerShadow(Double.parseDouble(parametros[0]), Double.parseDouble(parametros[1]), Double.parseDouble(parametros[2]), javafx.scene.paint.Color.valueOf(parametros[3]));
    }

    private Effect convertirGlow(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.Glow(Double.parseDouble(parametros[0]));
    }

    private Effect convertirGaussianBlur(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.GaussianBlur(Double.parseDouble(parametros[0]));
    }

    private Effect convertirDropShadow(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.DropShadow(Double.parseDouble(parametros[0]), Double.parseDouble(parametros[1]), Double.parseDouble(parametros[2]), javafx.scene.paint.Color.valueOf(parametros[3]));
    }

    private Effect convertirColorAdjust(String efecto) {
        String[] parametros = efecto.split(",");
        return new javafx.scene.effect.ColorAdjust(Double.parseDouble(parametros[0]), Double.parseDouble(parametros[1]), Double.parseDouble(parametros[2]), Double.parseDouble(parametros[3]));
    }
}
