package nucleo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Manuel Landín Gómez
 * @author Iago Leirós Pérez
 */
public class Bolsa {

    private static final String[] LETRAS = {"A", "B", "C", "CH", "D", "E", "F", "G", "H", "I", "J", "L", "LL", "M", "N", "Ñ", "O", "P", "Q", "R", "RR", "S", "T", "U", "V", "X", "Y", "Z", "#"};
    private static final int[] CANTIDAD_LETRAS = {14, 3, 5, 1, 6, 13, 2, 2, 2, 7, 1, 6, 1, 4, 6, 1, 10, 3, 1, 6, 1, 7, 5, 7, 2, 1, 1, 1, 2};
    private static final int[] VALORES = {1, 3, 3, 5, 2, 1, 4, 2, 4, 1, 8, 1, 8, 3, 1, 10, 1, 3, 10, 1, 8, 1, 1, 1, 4, 8, 4, 10, 0};
    private static final boolean[] COMODIN = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true};

    private ArrayList<Letra> letrasBolsa = new ArrayList<>();

    
    
    public Bolsa() {
        this(LETRAS, CANTIDAD_LETRAS, VALORES, COMODIN);
    }
    /**
     * Construye una bolsa con <code>cantidad</code> de letras de grafía
     * <code>letra</code> junto a su <code>valor</code>.
     *
     * @param letra
     * @param cantidad
     * @param valor
     */
    public Bolsa(String[] letra, int[] cantidad, int[] valor) {
        for (int i = 0; i < letra.length; i++) {
            for (int j = 0; j < cantidad[i]; j++) {
                letrasBolsa.add(new Letra(letra[i], valor[i]));
            }
        }
    }

    /**
     * Construye una bolsa con <code>cantidad</code> de letras de grafía
     * <code>letra</code> junto a su <code>valor</code> y su consideración de
     * objeto <code>comodin</code>.
     *
     * @param letra
     * @param cantidad
     * @param valor
     * @param comodin
     */
    public Bolsa(String[] letra, int[] cantidad, int[] valor, boolean[] comodin) {
        for (int i = 0; i < letra.length; i++) {
            for (int j = 0; j < cantidad[i]; j++) {
                letrasBolsa.add(new Letra(letra[i], valor[i], comodin[i]));
            }
        }
    }

    public ArrayList<Letra> getLetrasBolsa() {
        return letrasBolsa;
    }

    /**
     * Se extrae una letra aleatoria de <code>this</code> que se devuelve como
     * retorno del método siempre y cuando <code>this</code> no esté vacía. En
     * tal caso se devuelve <code>null</code>.
     *
     * @return <code>Letra</code>/<code>null</code>.
     */
    public Letra extraerLetraAleatoria() {
        Letra select;
        if (letrasBolsa.size() > 0) {
            int index = new Random().nextInt(letrasBolsa.size());
            select = letrasBolsa.get(index);
            letrasBolsa.remove(index);
        } else {
            select = null;
        }
        return select;

    }

    @Override
    public String toString() {
        return Arrays.toString(letrasBolsa.toArray());
    }

}
