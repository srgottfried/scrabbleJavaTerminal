package nucleo;


/**
 * Abstracción del objeto letra. Trata cada una de las letras del juego como un
 * objeto independiente con sus características propias.
 *
 * @author Manuel Landín Gómez
 * @author Iago Leirós Pérez
 */
public class Letra {

    private String letra;
    private final int VALOR;
    private final boolean esComodin;



    public Letra(String letra, int valor) {
        this(letra, valor, false);
    }

    public Letra(String letra, int valor, boolean esComodin) {
        this.letra = letra;
        this.VALOR = valor;
        this.esComodin = esComodin;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public int getVALOR() {
        return VALOR;
    }

    public boolean isEsComodin() {
        return esComodin;
    }

    

    @Override
    public String toString() {
        return letra;
    }

    
}
