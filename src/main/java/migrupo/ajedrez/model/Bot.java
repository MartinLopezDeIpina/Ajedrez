package migrupo.ajedrez.model;

import java.util.List;

public class Bot extends Usuario{

    GestorDeMovimientos mGestorDeMovimientos;
    public Bot(String nombre, String contrasena){
        super(nombre, contrasena);

        mGestorDeMovimientos = GestorDeMovimientos.getInstance();
    }

    public void iniciarTurno(){
        Movimiento movimiento = elegirMovimiento();

        mGestorDeMovimientos.hacerMovimientoPasarTurnoYGuardarMovimiento(movimiento);
    }

    private Movimiento elegirMovimiento() {
        List<Movimiento> movimientosPosibles = mGestorDeMovimientos.getTodosLosMovimientosPosibles(this.color);

        return movimientosPosibles.get((int) (Math.random() * movimientosPosibles.size()));
    }
}
