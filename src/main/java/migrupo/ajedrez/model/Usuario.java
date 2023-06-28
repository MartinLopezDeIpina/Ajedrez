package migrupo.ajedrez.model;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Usuario {
    protected SimpleStringProperty nombre = new SimpleStringProperty();

    protected String contrasena;
    protected Color color;

    protected Usuario(String nombre, String contrasena){
        this.nombre.set(nombre);
        this.contrasena = contrasena;
    }

    public String getNombreValue(){return nombre.getValue();}
    public SimpleStringProperty getNombre(){return nombre;}
    public void setColor(Color color){this.color = color;}
    public Color getColor(){return color;}
}
