package migrupo.ajedrez.model;

public enum RazonVictoria {
    JACKE_MATE("Jacke Mate"), REY_AHOGADO("Rey Ahogado"), MATERIAL_INSUFICIENTE("Material Insuficiente");

    private String nombre;

    RazonVictoria(String nombre){
        this.nombre = nombre;
    }

    public String toString(){
        return nombre;
    }

    public boolean equalsRazon(RazonVictoria razonVictoria){
        return this.toString().equals(razonVictoria.toString());
    }
}
