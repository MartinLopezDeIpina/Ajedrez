package migrupo.ajedrez.model;

public class Tablero {
    private Tablero(){

    }
    private final static Tablero mTablero = new Tablero();
    public Tablero getInstance(){return mTablero;}

    private Casilla[][] casillas = {
            {new Casilla('a', 1), new Casilla('b', 1), new Casilla('c', 1), new Casilla('d', 1), new Casilla('e', 1), new Casilla('f', 1), new Casilla('g', 1), new Casilla('h', 1)},
            {new Casilla('a', 2), new Casilla('b', 2), new Casilla('c', 2), new Casilla('d', 2), new Casilla('e', 2), new Casilla('f', 2), new Casilla('g', 2), new Casilla('h', 2)},
            {new Casilla('a', 3), new Casilla('b', 3), new Casilla('c', 3), new Casilla('d', 3), new Casilla('e', 3), new Casilla('f', 3), new Casilla('g', 3), new Casilla('h', 3)},
            {new Casilla('a', 4), new Casilla('b', 4), new Casilla('c', 4), new Casilla('d', 4), new Casilla('e', 4), new Casilla('f', 4), new Casilla('g', 4), new Casilla('h', 4)},
            {new Casilla('a', 5), new Casilla('b', 5), new Casilla('c', 5), new Casilla('d', 5), new Casilla('e', 5), new Casilla('f', 5), new Casilla('g', 5), new Casilla('h', 5)},
            {new Casilla('a', 6), new Casilla('b', 6), new Casilla('c', 6), new Casilla('d', 6), new Casilla('e', 6), new Casilla('f', 6), new Casilla('g', 6), new Casilla('h', 6)},
            {new Casilla('a', 7), new Casilla('b', 7), new Casilla('c', 7), new Casilla('d', 7), new Casilla('e', 7), new Casilla('f', 7), new Casilla('g', 7), new Casilla('h', 7)},
            {new Casilla('a', 8), new Casilla('b', 8), new Casilla('c', 8), new Casilla('d', 8), new Casilla('e', 8), new Casilla('f', 8), new Casilla('g', 8), new Casilla('h', 8)}
    };

}
