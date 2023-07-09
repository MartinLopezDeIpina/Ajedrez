package migrupo.ajedrez.model.StateCasilla;

public class EstadoCasillaSeleccionado implements EstadoCasilla{

    private final Casilla casilla;

    public EstadoCasillaSeleccionado(Casilla casilla){
        this.casilla = casilla;
    }

    @Override
    public void seleccionarCasilla() {

    }

    @Override
    public void deseleccionarCasilla() {
        casilla.setEstadoCasilla(new EstadoCasillaNormal(casilla));
    }
}
