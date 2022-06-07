package nucleo;

import esteticos.Icono;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import utilidades.Lector;
import esteticos.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import static nucleo.Mano.enMano;

/**
 * Abstracción del tablero de juego.
 *
 * @author Manuel Landín Gómez
 * @author Iago Leirós Pérez
 */
public class Tablero {

    private final Scanner sc = new Scanner(System.in);
    private Letra[][] tablero;
    private Premio[][] mascaraPremios;
    private final Bolsa BOLSA;
    private ArrayList<Jugador> listaJugadores;
    private boolean primeraInsercion;
    private int puntosPalabra;

    public Tablero(int filas, int columnas) {
        BOLSA = new Bolsa();
        listaJugadores = new ArrayList<>();
        tablero = new Letra[filas][columnas];
        mascaraPremios = new Premio[filas][columnas];
        primeraInsercion = true;
        
        generarPremios(filas, columnas, 3, 4, 5, 2);
        crearJugadores();
        generarRondas();
        mostrarPuntuacion();

    }

    /**
     * Genera los distintos premios indicados, agregándolos a la lista de
     * premios.
     *
     * @param filas
     * @param columnas
     * @param numPremio2
     * @param numPremio3
     * @param numPremio4
     * @param numPremioPalabra
     */
    private void generarPremios(int filas, int columnas, int numPremio2, int numPremio3, int numPremio4, int numPremioPalabra) {
        ArrayList<Premio> listaPremios = new ArrayList<>();
        //premio letra x2
        for (int i = 0; i < numPremio2; i++) {
            listaPremios.add(new Premio(2));
        }
        //premio letra x3
        for (int i = 0; i < numPremio3; i++) {
            listaPremios.add(new Premio(3));
        }
        //premio letra x4
        for (int i = 0; i < numPremio4; i++) {
            listaPremios.add(new Premio(4));
        }
        //premio palabra x2
        for (int i = 0; i < numPremioPalabra; i++) {
            listaPremios.add(new Premio(2, true));
        }

        asignacionAleatoria(listaPremios, filas, columnas);

    }

    /**
     * Asigna una lista de premios de manera aleatoria a un tablero de filas x
     * columnas
     *
     * @param listaPremios
     * @param filas
     * @param columnas
     */
    private void asignacionAleatoria(ArrayList<Premio> listaPremios, int filas, int columnas) {
        boolean asignado = false;
        ArrayList<Integer> indicesPremio;
        int auxIndex;
        Random rdm = new Random();
        indicesPremio = new ArrayList<>();

        do {
            auxIndex = rdm.nextInt(filas * columnas);
            if (indicesPremio.isEmpty()) {
                indicesPremio.add(auxIndex);
            } else {
                if (!indicesPremio.contains(auxIndex)) {
                    indicesPremio.add(auxIndex);
                }
            }
        } while (indicesPremio.size() < listaPremios.size());

        int k = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                k++;
                if (indicesPremio.contains(k)) {

                    mascaraPremios[i][j] = listaPremios.get(rdm.nextInt(indicesPremio.size()));
                }

            }
        }

    }

    /**
     * Inicia la creación de jugadores
     */
    private void crearJugadores() {
        int numJugadores;
        System.out.println(Color.FONDO_GRIS + " Número de xogadores (1-4)" + Color.RESET);
        System.out.print(Icono.LAPIZ + " ");
        numJugadores = Lector.entero(1, 5);
        for (int i = 0; i < numJugadores; i++) {
            String auxNombre;
            do {
                System.out.println("");
                System.out.printf(Color.FONDO_GRIS + " Nome xogador %d " + Color.RESET + "\n", i + 1);
                System.out.print(Icono.LAPIZ + " ");
                auxNombre = sc.nextLine();
                if (auxNombre.isEmpty() || auxNombre.isBlank()) {
                    System.out.println("Nome inválido.");
                }
            } while (auxNombre.isEmpty() || auxNombre.isBlank());
            listaJugadores.add(new Jugador(auxNombre));
            listaJugadores.get(i).getMano().llenarMano(BOLSA);
        }
    }

    /**
     * Genera la secuencia de sucesos completos para una ronda
     *
     * @param numRonda
     */
    private void generarRonda(int numRonda) {
        Iterator<Jugador> it = listaJugadores.iterator();
        boolean control;
        while (it.hasNext()) {
            Jugador auxJugador = it.next();
            String palabra;
            do {
                System.out.println("");
                System.out.printf(Color.FONDO_GRIS + " RONDA %d \n" + Color.RESET, numRonda);
                System.out.println(this.toString());
                System.out.print(Color.FONDO_MAGENTA + Color.GRIS + " " + Icono.PEON + " " + auxJugador.getNombre() + "  " + Color.RESET);
                System.out.println(Color.FONDO_VERDE + Color.GRIS + " " + auxJugador.getPuntuacion() + Icono.ESTRELLA + " " + Color.RESET);
                System.out.println(Color.FONDO_GRIS + auxJugador.getMano().toString() + Color.RESET);

                System.out.println("");
                System.out.print("Palabra: ");
                palabra = Lector.palabra();

                if (!palabra.equals("-1")) {
                    if (!primeraInsercion) {
                        System.out.print("Posición (fila,columna,orientación): ");
                        String[] posicion;
                        boolean orientacion;
                        int fila, columna;

                        posicion = Lector.inputPalabra();

                        fila = Integer.parseInt(posicion[0]);
                        columna = Integer.parseInt(posicion[1]);
                        orientacion = posicion[2].equalsIgnoreCase("h");

                        control = this.insertarPalabra(fila, columna, orientacion, palabra, auxJugador);
                    } else {
                        control = this.insertarPalabra(posicionamiento(palabra)[0], posicionamiento(palabra)[1], true, palabra, auxJugador);
                        if (control) {
                            primeraInsercion = false;
                        }
                    }

                } else {
                    control = true;
                    auxJugador.incrementarChina(); //incrementamos las chinas cada vez que se pasa turno
                    renovarLetraEnMano(auxJugador); //renovamos letra en mano al pasar turno
                }
                auxJugador.getMano().llenarMano(BOLSA);
            } while (!control && auxJugador.getMano().getLetrasMano().size() > 0);

        }

    }

    /**
     * Renueva una letra aleatoria de la mano. Esta renovación no afecta a los
     * comodines.
     */
    private void renovarLetraEnMano(Jugador jugador) {
        ArrayList<Letra> letrasMano = jugador.getMano().getLetrasMano();
        Random rd = new Random();
        int seleccion;

        do {
            seleccion = rd.nextInt(letrasMano.size());
        } while (jugador.getMano().getLetrasMano().get(seleccion).isEsComodin());
        jugador.getMano().getLetrasMano().remove(seleccion);
        jugador.getMano().llenarMano(BOLSA);

    }

    /**
     * Calcula la posición de la palabra inicial en función de las dimensiones
     * del tablero y la longitud de la palabra.
     *
     * @param palabra
     * @return
     */
    private int[] posicionamiento(String palabra) {
        int[] posicion = new int[2];
        int x = tablero.length;
        int y = tablero[0].length;
        int l = palabra.length();

        posicion[0] = (y / 2);
        posicion[1] = (x / 2) - (l / 2);

        return posicion;
    }

    /**
     * Genera el bucle de rondas hasta quese cumplan las condiciones de
     * finalización.
     */
    private void generarRondas() {
        int i = 0;
        do {
            i++;
            generarRonda(i);
        } while (siguienteRonda());
    }

    /**
     * Evalua condiciones para pasar a la siguiente ronda.
     *
     * @return si pasa o no a la sigueinte ronda
     */
    private boolean siguienteRonda() {
        Iterator<Jugador> it = listaJugadores.iterator();
        boolean siguienteRonda = true;

        //algún jugador pasa 3 veces
        while (it.hasNext() && siguienteRonda) {
            Jugador jugador = it.next();
            if (jugador.getChinas() >= 3) {
                siguienteRonda = false;
            }
        }

        //se acaban las fichas
        it = listaJugadores.iterator();
        while (siguienteRonda && BOLSA.getLetrasBolsa().isEmpty() && it.hasNext()) {
            Jugador jugador = it.next();
            if (jugador.getMano().getLetrasMano().isEmpty()) {
                siguienteRonda = false;
            }
        }

        return siguienteRonda;
    }

    /**
     * Inserta la <code>palabra</code> en la posición indicada
     * <code>(fila,columna,orientacion)</code> si se cumplen todos lo
     * requerimientos.
     *
     * @param fila
     * @param columna
     * @param orientacion
     * @param palabra
     * @param jugador
     * @return si palabra insertada con éxito.
     */
    private boolean insertarPalabra(int fila, int columna, Boolean orientacion, String palabra, Jugador jugador) {
        String[] letrasPalabra = diseccionarPalabra(palabra);
        boolean insertada = true;

        // estamos en rango de tablero
        // vemos si todas las letras de palabra están en la mano o en el tablero
        // la interseccion en el tablero es correcta
        if (enRangoDeTablero(letrasPalabra, fila, columna, orientacion) && enManoOTablero(jugador, palabra, fila, columna, orientacion) && interseccionCorrecta(fila, columna, orientacion, palabra)) {
            if (orientacion) {
                for (int i = columna, index = 0; index < letrasPalabra.length; i++, index++) {
                    insertarLetra(fila, i, letrasPalabra[index], jugador);
                }
            } else {
                for (int i = fila, index = 0; index < letrasPalabra.length; i++, index++) {
                    insertarLetra(i, columna, letrasPalabra[index], jugador);
                }
            }
            System.out.println(Color.FONDO_GRIS + "   " + Color.VERDE + " +" + puntosPalabra + Icono.ESTRELLA + " " + Color.RESET);
        } else {
            System.out.println("\n" + Color.FONDO_ROJO + Color.GRIS + " " + Icono.AVISO + " ERRO DE INSERCIÓN " + Color.RESET);
            if (!enRangoDeTablero(letrasPalabra, fila, columna, orientacion)) {
                System.out.println(Color.ROJO + Icono.AVISO + " Salícheste do taboleiro" + Color.RESET);
            } else if (!enManoOTablero(jugador, palabra, fila, columna, orientacion)) {
                System.out.println(Color.ROJO + Icono.AVISO + " Falta unha letra necesaria na palabra" + Color.RESET);
            } else if (!interseccionCorrecta(fila, columna, orientacion, palabra)) {
                System.out.println(Color.ROJO + Icono.AVISO + " A palabra inserida non interseca adecuadamente" + Color.RESET);
            }
            insertada = false;
        }
        
        // hacemos Scrabble cuando gastamos las 7 letras de la mano (todas)
        if (jugador.getMano().getLetrasMano().isEmpty()) {
            jugador.hacerScrabble();
            System.out.println("\n" + Color.FONDO_VERDE + Color.GRIS + " " + " SCRABBLE: +50" + Icono.ESTRELLA + " " + Color.RESET);
        }
        
        puntosPalabra = 0;
        return insertada;

    }

    /**
     * Verifica que las letras de la palabra introducida cumplan las condiciones
     * de estar en la mano o en el tablero.
     *
     * @param jugador
     * @param palabra
     * @param fila
     * @param columna
     * @param orientacion
     * @return si letras de palabra están en mano o tablero
     */
    private boolean enManoOTablero(Jugador jugador, String palabra, int fila, int columna, boolean orientacion) {
        Mano auxMano = jugador.getMano().clonador();
        boolean encontrado = true;
        String[] letrasPalabra = diseccionarPalabra(palabra);
        for (int i = 0; i < letrasPalabra.length && encontrado; i++) {
            if (orientacion) {
                if (tablero[fila][columna + i] != null) {
                    encontrado = tablero[fila][columna + i].getLetra().equalsIgnoreCase(letrasPalabra[i]);
                    if (!encontrado) {
                        encontrado = enMano(letrasPalabra[i], auxMano);
                    }
                } else {
                    encontrado = enMano(letrasPalabra[i], auxMano);
                }
            } else {
                if (tablero[fila + i][columna] != null) {
                    encontrado = tablero[fila + i][columna].getLetra().equalsIgnoreCase(letrasPalabra[i]);
                    if (!encontrado) {
                        encontrado = enMano(letrasPalabra[i], auxMano);
                    }
                } else {
                    encontrado = enMano(letrasPalabra[i], auxMano);
                }

            }
        }

        return encontrado;
    }

    /**
     * Se asegura de la correcta intersección de la palabra en el tablero.
     *
     * @param fila
     * @param columna
     * @param orientacion <code>h</code> - horizontal / <code>v</code> -
     * vertical
     * @param palabra
     * @return si intersección correcta
     */
    private boolean interseccionCorrecta(int fila, int columna, Boolean orientacion, String palabra) {
        String[] auxPalabra = diseccionarPalabra(palabra);
        boolean control = true;
        int contador = 0;
        if (orientacion) {
            for (int j = columna, c = 0; c < auxPalabra.length && control; j++, c++) {
                if (tablero[fila][j] != null) {
                    control = tablero[fila][j].getLetra().equalsIgnoreCase(auxPalabra[c]);
                } else {
                    contador++;
                }
            }
        } else {

            for (int i = fila, c = 0; c < auxPalabra.length && control; i++, c++) {
                if (tablero[i][columna] != null) {
                    control = tablero[i][columna].getLetra().equalsIgnoreCase(auxPalabra[c]);
                } else {
                    contador++;
                }
            }
        }
        //controlamos que la palabra interseque al menos una vez a otra palabra
        //a excepción de la primera inserción.
        if (contador == palabra.length()) {
            control = false || primeraInsercion;
        }
        return control;
    }

    /**
     * Inserta una letra de la mano de un jugador en el tablero siempre y cuando
     * el jugador disponga de dicha letra en su mano.
     *
     * @param fila
     * @param columna
     * @param letra
     * @param jugador
     * @return si la inserción ha sido válida. Insertar no implica gastar una
     * letra de la mano, ya que si insertamos una letra donde ya hay una
     * (igual), podrá realizarse la inserción pero no descontará dicha letra de
     * la mano. Insertar una letra fuera del rango del teclado o en una posición
     * ya ocupada por una letra distinta a la que se pretende introducir
     * devolverá un false.
     */
    private boolean insertarLetra(int fila, int columna, String letra, Jugador jugador) {
        //hacemos uso de la letra. Si la mano de jugador no posee letra, recibimos un false.
        //si posee letra, la ELIMINA de la mano y nos retorna true
        Letra letraMano;
        boolean control;
        int puntos;

        if (tablero[fila][columna] == null) {
            letraMano = jugador.getMano().usarLetra(letra);
        } else {
            letraMano = tablero[fila][columna];
        }

        if (tablero[fila][columna] == null) {
            tablero[fila][columna] = letraMano;
            control = true;
        } else {
            control = tablero[fila][columna].getLetra().equalsIgnoreCase(letra);
        }
        // puntuamos la letra insertada. Se puntúa tanto si la letra se usa de la mano
        // como si existe una ocurrencia con una letra que ya está en el tablero.
        if (letraMano != null) {
            int premio = premiar(fila, columna);
            puntos = (letraMano.getVALOR() * premio);
            puntosPalabra += puntos;
            jugador.incrementarPuntuacion(puntos);
            int mult;
            boolean afectaPalabra = false;
            if (mascaraPremios[fila][columna] != null) {
                mult = mascaraPremios[fila][columna].getMultiplicador();
                afectaPalabra = mascaraPremios[fila][columna].isAfectaAPalabra();
            } else {
                mult = 1;
            }
            System.out.print(Color.FONDO_GRIS + Color.NEGRO + " " + letra  + " " + Color.RESET + " " + Color.VERDE + "+" + letraMano.getVALOR() + Icono.ESTRELLA + Color.RESET);
            if (mult > 1 && !afectaPalabra && !mascaraPremios[fila][columna].isUsado()) {
                System.out.print(Color.AMARILLO + " (x" + mascaraPremios[fila][columna].getMultiplicador() + ")" + Color.RESET);
                mascaraPremios[fila][columna].usarPremio();
            }
            System.out.println("");
        } else if (letraMano == null && control) {
            int premio = premiar(fila, columna);
            puntos = tablero[fila][columna].getVALOR() * premio;
            puntosPalabra += puntos;
            int mult;
            boolean afectaPalabra = false;
            if (mascaraPremios[fila][columna] != null) {
                mult = premio;
                afectaPalabra = mascaraPremios[fila][columna].isAfectaAPalabra();
            } else {
                mult = 1;
            }
            System.out.print(Color.FONDO_GRIS + Color.NEGRO + " " + letra + " " + Color.RESET + Color.VERDE + "+" + tablero[fila][columna].getVALOR() + Icono.ESTRELLA + Color.RESET);
            if (mult > 1 && !afectaPalabra && !mascaraPremios[fila][columna].isUsado()) {
                System.out.print(Color.AMARILLO + " (x" + mascaraPremios[fila][columna].getMultiplicador() + ")" + Color.RESET);
                mascaraPremios[fila][columna].usarPremio();
            }
            System.out.println("");
            jugador.incrementarPuntuacion(puntos);

        }
        return control;
    }

    /**
     * Genera el multiplicador adecuado para la casilla correspondiente si en la
     * posicion hubiera un premio
     *
     * @param fila
     * @param columna
     * @return multiplicador del premio
     */
    private int premiar(int fila, int columna) {
        int multiplicador = 1;
        Premio premio = mascaraPremios[fila][columna];
        if (premio != null && !premio.isAfectaAPalabra() && !premio.isUsado()) {
            multiplicador = premio.getMultiplicador();
        }
        return multiplicador;
    }

    /**
     * Disecciona la palabra de entrada en partículas separadas por un - donde
     * cada partícula es una letra válida del juego.
     *
     * @param palabra
     * @return array con elementos las partículas ve la palabra.
     */
    private String[] diseccionarPalabra(String palabra) {
        StringBuilder diseccion = new StringBuilder("");
        palabra = palabra.toUpperCase();

        //búsqueda de letras dobles
        int[] mask = new int[palabra.length()];
        Arrays.fill(mask, 0);
        int index = 0;
        String subString[] = {"CH", "LL", "RR"}; //letras dobles consideradas

        for (String x : subString) {
            if (palabra.contains(x)) {
                while (palabra.indexOf(x, index) > -1) {
                    index = palabra.indexOf(x, index);
                    mask[index] = 1;
                    index++;
                }
            }
            index = 0;
        }

        for (int i = 0; i < mask.length; i++) {
            if (mask[i] == 1) {
                diseccion.append(palabra.substring(i, i + 2));
                i++;
            } else {
                diseccion.append(palabra.substring(i, i + 1));
            }
            if (i < mask.length - 1) {
                diseccion.append("-");
            }

        }
        return diseccion.toString().split("-");

    }

    /**
     * Informa si una palabra entra en el tablero <code>true</code> o se sale
     * fuera <code>false</code>.
     *
     * @param palabra
     * @param fila
     * @param columna
     * @return si palabra se encuentra en rango de tablero
     */
    private boolean enRangoDeTablero(String[] letrasPalabra, int fila, int columna, boolean orientacion) {
        boolean enRango;
        enRango = (orientacion) ? enRangoX(letrasPalabra, fila, columna) : enRangoY(letrasPalabra, fila, columna);
        return enRango;
    }

    private boolean enRangoX(String[] letrasPalabra, int fila, int columna) {
        boolean enRango = false;
        if (0 <= fila && fila < tablero.length) {
            enRango = tablero.length >= letrasPalabra.length + columna;
        }
        return enRango;
    }

    private boolean enRangoY(String[] letrasPalabra, int fila, int columna) {
        boolean enRango = false;
        if (0 <= columna && columna < tablero[0].length) {
            enRango = tablero[columna].length >= letrasPalabra.length + fila;
        }
        return enRango;
    }

    @Override
    public String toString() {
        String hline = Color.FONDO_GRIS + "+----";
        String vline = Color.FONDO_GRIS + "|";
        StringBuilder tab = new StringBuilder("");

        char indicadorX = 64;

        tab.append(Color.FONDO_GRIS + "    " + Color.RESET);
        for (int j = 0; j < tablero[0].length; j++) {
            tab.append(Color.FONDO_GRIS + "     " + Color.RESET);
        }
        tab.append(Color.FONDO_GRIS + " \n" + Color.RESET);
        tab.append(Color.FONDO_GRIS + "    " + Color.RESET);
        for (int j = 0; j < tablero[0].length; j++) {
            indicadorX++;
            tab.append(Color.FONDO_GRIS + "  " + Color.FONDO_AZUL + Color.GRIS + " ").append(indicadorX).append(" " + Color.FONDO_GRIS + Color.RESET);
        }
        tab.append(Color.FONDO_GRIS + " " + Color.RESET + "\n");

        tab.append(Color.FONDO_GRIS + "    " + Color.RESET);
        for (int j = 0; j < tablero[0].length; j++) {
            tab.append(hline);
        }
        tab.append("+\n");

        for (int i = 0; i < tablero.length; i++) {
            if (i < 9) {
                tab.append(Color.FONDO_GRIS + " " + Color.RESET);
            }
            tab.append(Color.FONDO_AZUL + Color.GRIS + " " + (i + 1) + " " + Color.RESET);
            tab.append(vline);
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] != null) {
                    tab.append(" ");
                    tab.append(tablero[i][j].getLetra().toUpperCase());
                    if (tablero[i][j].getLetra().length() < 2) {
                        tab.append("  ");
                    } else {
                        tab.append(" ");
                    }
                } else {
                    if (mascaraPremios[i][j] != null) {
                        tab.append(" ");
                        tab.append(mascaraPremios[i][j].getGrafia());
                        tab.append(" ");
                    } else {
                        tab.append("    ");
                    }

                }
                tab.append(vline);

            }
            tab.append("\n");
            tab.append(Color.FONDO_GRIS + "    " + Color.RESET);
            for (int j = 0; j < tablero[i].length; j++) {
                tab.append(hline);
            }
            tab.append("+\n");
        }
        tab.append(Color.RESET);
        return tab.toString();
    }
    
    
    /**
     * Amosa unha tabla cos resultados finais da partida
     */
    private void mostrarPuntuacion() {
        Scanner sc = new Scanner(System.in);
        int INI_NUM_PLAYERS = listaJugadores.size();int i = 0;
        Collections.sort(listaJugadores, (Jugador j1, Jugador j2) -> new Integer(j2.getPuntuacion()).compareTo(new Integer(j1.getPuntuacion())));
        Iterator<Jugador> it = listaJugadores.iterator();

        System.out.println("========================================");
        System.out.println(" \uD83D\uDC51\tXogador\t\tScore");
        System.out.println("========================================");
        while (it.hasNext()) {
            i++;
            Jugador jugador = it.next();
            System.out.printf(" %d\t\u001B[34m%s\u001B[30m\t\t%d\u001B[33m\u2605\u001B[30m\n", i, jugador.getNombre(), jugador.getPuntuacion());
        }
        System.out.println("========================================");
        System.out.println("Premer \u001B[35mENTER\u001B[30m para voltar ao menú.");
        sc.nextLine();
    }
    
   


}
