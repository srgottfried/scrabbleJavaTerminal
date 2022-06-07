package nucleo;

import esteticos.Color;
import esteticos.Icono;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Manuel Landín Gómez
 * @author Iago Leirós Pérez
 */
public class Mano {

    private Jugador propietario;
    private ArrayList<Letra> letrasMano = new ArrayList<>();
    private final int NUM_MAX_ELEM = 7;
    private static Iterator<Letra> it;

    public Mano() {

    }

    /**
     *
     * @param mano
     */
    public Mano(Mano mano) {

    }

    /**
     * Genera una mano auxiliar como clon de la mano original para hacer
     * operaciones sobre sus elementos sin afectar a los elementos originales.
     *
     * @return clon de la mano
     */
    public Mano clonador() {
        Mano auxMano = new Mano();
        auxMano.setPropietario(propietario);
        auxMano.setLetrasMano((ArrayList) letrasMano.clone());

        return auxMano;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    public ArrayList<Letra> getLetrasMano() {
        return letrasMano;
    }

    public void setLetrasMano(ArrayList<Letra> letrasMano) {
        this.letrasMano = letrasMano;
    }

    public boolean isCompleta() {
        return letrasMano.size() - NUM_MAX_ELEM == 0;
    }

    /**
     * Llena la mano de letras que toma del objeto estático
     * <code>BOLSA</code>.Si la bolsa no continene las suficientes letras para
     * llenar la mano, la mano quedará a medio llenar.
     *
     * @param bolsa
     */
    public void llenarMano(Bolsa bolsa) {
        boolean stop = false;
        while (!isCompleta() && !stop) {
            if (!agregarLetra(bolsa)) {
                stop = true;
            }
        }
    }

    /**
     * Agrega letra aleatoria de la bolsa a la mano.
     *
     * @return si ha habido éxito
     */
    private boolean agregarLetra(Bolsa bolsa) {
        boolean control = true;

        if (!isCompleta() && !bolsa.getLetrasBolsa().isEmpty()) {
            letrasMano.add(bolsa.extraerLetraAleatoria());
        } else {
            control = false;
            System.out.println(Color.ROJO + Icono.AVISO + " No se puede robar más letras. La bolsa está vacía." + Color.RESET);
        }

        return control;

    }

    /**
     * Busca en la mano el objeto letra que tiene como grafía
     * <code>letra</code>. Si hay coincidencia, retorna el primer objeto letra
     * encontrado. Si no hay coincidencia retorna <code>null</code>.
     *
     * @param letra letra que queremos usar.
     * @return objeto letra usado o <code>null</code> si en la mano no está la
     * letra buscada.
     */
    public Letra usarLetra(String letra) {
        boolean borrado = false;
        Letra auxLetra = null;
        if (!letrasMano.isEmpty() && enMano(letra)) {
            it = letrasMano.iterator();

            while (it.hasNext() && !borrado) {
                auxLetra = it.next();

                if (auxLetra.getLetra().equalsIgnoreCase(letra)) {
                    letrasMano.remove(auxLetra);
                    borrado = true;
                }
            }
            if (!borrado) {
                it = letrasMano.iterator();
                while (it.hasNext() && !borrado) {
                    auxLetra = it.next();

                    if (auxLetra.isEsComodin()) {
                        auxLetra.setLetra(letra);
                        letrasMano.remove(auxLetra);
                        borrado = true;
                    }
                }
            }

        }

        return auxLetra;
    }

    /**
     * Informa si la letra está en la mano o no. Primero hace una búsqueda sobre
     * las letras. Si no encuentra la letra buscada, entonces hace una nueva
     * búsqueda teniendo en cuenta los comodines.
     *
     * @param letra
     * @return si letra o comodín está en la mano.
     */
    public boolean enMano(String letra) {
        boolean encontrado = false;

        //analiza si la letra está en la mano sin contar a los comodines
        it = letrasMano.iterator();
        while (it.hasNext() && !encontrado) {
            Letra auxLetra = it.next();
            encontrado = auxLetra.getLetra().equalsIgnoreCase(letra);
        }

        // en caso de que no haya letra en mano, busca por comodines
        if (!encontrado) {
            it = letrasMano.iterator();
            while (it.hasNext() && !encontrado) {
                Letra auxLetra = it.next();
                encontrado = auxLetra.isEsComodin();
            }
        }

        return encontrado;
    }

    /**
     * Informa si la letra está en la mano pasada como parámetro. Primero hace
     * una búsqueda sobre las letras. Si no encuentra la letra buscada, entonces
     * hace una nueva búsqueda teniendo en cuenta los comodines.
     *
     * @param letra
     * @param mano
     * @return
     */
    public static boolean enMano(String letra, Mano mano) {
        boolean encontrado = false;

        //analiza si la letra está en la mano sin contar a los comodines
        it = mano.getLetrasMano().iterator();
        while (it.hasNext() && !encontrado) {
            Letra auxLetra = it.next();
            encontrado = auxLetra.getLetra().equalsIgnoreCase(letra);
            if (encontrado) {
                mano.getLetrasMano().remove(auxLetra);
            }
        }

        // en caso de que no haya letra en mano, busca por comodines.
        if (!encontrado) {
            it = mano.getLetrasMano().iterator();
            while (it.hasNext() && !encontrado) {
                Letra auxLetra = it.next();
                encontrado = auxLetra.isEsComodin();
                if (encontrado) {
                    mano.getLetrasMano().remove(auxLetra);
                }
            }
        }

        return encontrado;
    }

    /**
     * Informa si la lista de letras pasada por parámetro está en la mano.
     * Primero hace una búsqueda sobre las letras. Si no encuentra la letra
     * buscada, entonces hace una nueva búsqueda teniendo en cuenta los
     * comodines.
     *
     * @param listaLetras
     * @return si todas las letras de la lista pasada como parámetro están en la
     * mano
     */
    public boolean enMano(String[] listaLetras) {
        Mano auxMano = this.clonador();
        boolean encontrado = true;

        for (int i = 0; i < listaLetras.length && encontrado; i++) {
            encontrado = enMano(listaLetras[i], auxMano);
        }

        return encontrado;
    }

    @Override
    public String toString() {
        return Arrays.toString(letrasMano.toArray());
    }
}
