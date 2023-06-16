package migrupo.ajedrez.model;


import javafx.beans.property.SimpleObjectProperty;

public abstract class Usuario {
    protected SimpleObjectProperty<String> nombre = new SimpleObjectProperty<>();
    protected String contrasena;

    protected Usuario(String nombre, String contrasena){
        this.nombre.set(nombre);
        this.contrasena = contrasena;
    }

    public String getNombreValue(){return nombre.getValue();}
    public SimpleObjectProperty<String> getNombre(){return nombre;}
}
