package nucleo;


/**
 *
 * @author Manuel Landín Gómez
 * @author Iago Leirós Pérez
 */
public class Jugador {
    
    private Mano mano;
    private String nombre;
    private int puntuacion;
    int chinas = 0;
    
    /**
     * Genera un jugador con <code>nombre</code>. Para el jugador se genera
     * una mano inicial de 7 fichas y una puntuación inicial de 0.
     * @param nombre 
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        mano = new Mano();
        puntuacion = 0;
        chinas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getChinas() {
        return chinas;
    }

    
    public Mano getMano() {
        return mano;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    
    /**
     * Incrementa la puntuación en el valor indicado.
     * @param puntuacion que se quiere incrementar
     */
    public void incrementarPuntuacion(int puntuacion) {
        this.puntuacion += puntuacion;
    }
    
    /**
     * Incrementa china cuando se pasa de turno con -1
     */
    public void incrementarChina() {
        this.chinas++;
    }
    
    /**
     * Suma 50 puntos al hacer Scrabble.
     * Hacer Scrabble supone gastar las 7 letras de la mano.
     */
    public void hacerScrabble() {
        this.puntuacion += 50;
    }
    
    
}
