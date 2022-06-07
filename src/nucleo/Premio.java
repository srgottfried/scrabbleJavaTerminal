package nucleo;

/**
 *
 * @author Manuel Landín Gómez
 * @author Iago Leirós Pérez
 */
public class Premio {

    private final String grafia = "¿?";
    private int multiplicador;
    private final boolean afectaAPalabra;
    private boolean usado;

    public Premio(int multilicador, boolean afectaAPalabra) {
        this.multiplicador = multilicador;
        this.afectaAPalabra = afectaAPalabra;
        this.usado = false;
    }

    public Premio(int multiplicador) {
        this(multiplicador, false);
    }

    public String getGrafia() {
        return grafia;
    }

    public boolean isUsado() {
        return usado;
    }

    public int getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(int multiplicador) {
        this.multiplicador = multiplicador;
    }

    public boolean isAfectaAPalabra() {
        return afectaAPalabra;
    }

    public void usarPremio() {
        this.usado = true;
    }

}
