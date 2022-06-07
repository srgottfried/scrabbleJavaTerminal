package utilidades;

import esteticos.Color;
import esteticos.Icono;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author Manuel Landín Gómez
 * @author Iago Leirós Pérez
 */
public class Lector {

    private static final Scanner sc = new Scanner(System.in, StandardCharsets.ISO_8859_1);

    /**
     * Lee indefinidamente hasta que se introduzca un entero.
     *
     * @return entero leído por teclado.
     */
    public static int entero() {
        int entero;
        while (!sc.hasNextInt()) {
            System.out.println(Color.ROJO + Icono.AVISO + " Entrada inválida." + Color.RESET);
            sc.nextLine();
        }
        entero = sc.nextInt();
        sc.nextLine();
        return entero;
    }

    /**
     * Lee indefinidamente hasta que se introduzca un entero en rango.
     *
     * @param inicio incluído
     * @param fin no incluído
     * @return entero leído por teclado.
     */
    public static int entero(int inicio, int fin) {
        int entero;
        do {
            while (!sc.hasNextInt()) {
                System.out.println(Color.ROJO + Icono.AVISO + " Enteiro inválido." + Color.RESET);
                sc.nextLine();
            }
            entero = sc.nextInt();
            sc.nextLine();
            if (entero < inicio || entero >= fin) {
                System.out.printf(Color.ROJO + Icono.AVISO + " Enteiro fora de rango [%d,%d].\n" + Color.RESET, inicio, fin - 1);
            }
        } while (entero < inicio || entero >= fin);
        return entero;
    }

    /**
     * Lee indefinidamente hasta que se introduzca una palabra distinta de vacío
     * o blanco.
     *
     * @return palabra distinta de vacío o blanco.
     */
    public static String palabra() {
        String palabra;
        do {
            palabra = sc.nextLine().trim();
            if (palabra.isEmpty() || palabra.isBlank()) {
                System.out.print(Color.ROJO + Icono.AVISO + " Entrada inválida. Ténteo de novo: " + Color.RESET);
            }
        } while (palabra.isEmpty() || palabra.isBlank());
        return palabra;
    }

    /**
     * Lee indefinidamente hasta que se introduzca una palabra distinta de vacío
     * o blanco.
     *
     * @param longitud
     * @return palabra distinta de vacío o blanco.
     */
    public static String palabra(int longitud) {
        String palabra;
        do {
            palabra = sc.nextLine().trim();
            if (palabra.isEmpty() || palabra.isBlank() || palabra.length() != longitud) {
                System.out.print(Color.ROJO + Icono.AVISO + " Entrada inválida. Ténteo de novo: " + Color.RESET);
            }
        } while (palabra.isEmpty() || palabra.isBlank() || palabra.length() != longitud);
        return palabra;
    }

    /**
     * Lectura binaria de si/no
     *
     * @return elección.
     */
    public static boolean siNo() {
        String auxStr;

        do {
            auxStr = sc.nextLine();
            if (!auxStr.equalsIgnoreCase("S") && !auxStr.equalsIgnoreCase("N")) {
                System.out.println(Color.ROJO + Icono.AVISO + " Entrada inválida." + Color.RESET);
            }
        } while (!auxStr.equalsIgnoreCase("S") && !auxStr.equalsIgnoreCase("N") && !auxStr.isEmpty());

        return auxStr.equalsIgnoreCase("S") || auxStr.isEmpty();

    }

    /**
     * Lector binario que emplea los Strings
     * <code>comparador1, comparador2</code> como elementos de selección.
     *
     * @param comparador1 true
     * @param comparador2 false
     * @return elección.
     */
    public static boolean binario(String comparador1, String comparador2) {
        String auxStr;

        do {
            auxStr = sc.nextLine();
            if (!auxStr.equalsIgnoreCase(comparador1) && !auxStr.equalsIgnoreCase(comparador2)) {
                System.out.println(Color.ROJO + Icono.AVISO + " Entrada inválida." + Color.RESET);
            }
        } while (!auxStr.equalsIgnoreCase(comparador1) && !auxStr.equalsIgnoreCase(comparador2));

        return auxStr.equalsIgnoreCase(comparador1);
    }

    /**
     * Método estático que nos permite verificar la correcta entrada de valores
     * bajo las condiciones de entrada del juego Scrabble.
     * @return vector de posicionamiento en tablero.
     */
    public static String[] inputPalabra() {
        String[] palabra;
        boolean xValido, yValido, oValido;

        do {
            palabra = Lector.palabra().split(",");

            if (palabra != null && palabra.length == 3) {
                xValido = Character.isDigit(palabra[0].charAt(0));
                yValido = Character.isLetter(palabra[1].charAt(0));
                oValido = palabra[2].equalsIgnoreCase("h") || palabra[2].equalsIgnoreCase("v");
            } else {
                xValido = false;
                yValido = false;
                oValido = false;
            }

            if (!(xValido && yValido && oValido)) {
                System.out.print(Color.ROJO + Icono.AVISO + " Entrada inválida. Ténteo de novo: " + Color.RESET);
            }
        } while (!(xValido && yValido && oValido));
        palabra[0] = String.valueOf(Integer.parseInt(palabra[0]) - 1);
        palabra[1] = String.valueOf(palabra[1].toUpperCase().charAt(0) - 65);
        return palabra;
    }


}
