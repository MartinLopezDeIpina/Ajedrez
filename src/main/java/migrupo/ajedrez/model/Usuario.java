package migrupo.ajedrez.model;


public abstract class Usuario {
    protected String nombre;
    protected String contrasena;

    protected Usuario(String nombre, String contrasena){
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getNombre(){
        return nombre;
    }
}
