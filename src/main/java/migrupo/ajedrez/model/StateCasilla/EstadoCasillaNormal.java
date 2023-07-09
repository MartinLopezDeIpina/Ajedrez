package migrupo.ajedrez.model.StateCasilla;

public class EstadoCasillaNormal implements EstadoCasilla{

    private final Casilla casilla;

    public EstadoCasillaNormal(Casilla casilla){
        this.casilla = casilla;
    }

    @Override
    public void seleccionarCasilla() {
        casilla.setEstadoCasilla(new EstadoCasillaSeleccionado(casilla));
    }

    @Override
    public void deseleccionarCasilla() {

    }
}
