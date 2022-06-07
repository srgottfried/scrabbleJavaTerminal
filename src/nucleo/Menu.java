package nucleo;

import esteticos.Color;
import java.util.Scanner;
import utilidades.Lector;

/**
 * SCRABLLE VERSIÓN II
 * 
 * O xogo é unha versión adaptada do Scrabble á terminal de Java segundo
 * as especificacións propostas do proxecto.
 * Como base, este proxecto emprega a anterior versión do xogo do Scrabble.
 * Inclúense melloras complementarias ao proxecto. (Ver sección 1)
 * Pódense consultar as instruccións básicas de xogo na opción "Instruccións"
 * do menú inicial ou na sección 2 deste resumo.
 * 
 * 
 * 1. MELLORAS
 * Incorpóranse as seguintes melloras:
 *  1.1 Amosarase un menú inicial coas opcións que ten o xogador cando se lanza o programa.
 *  1.2 Poderase elixir o número de xogadores que comporán o xogo de 1 (solitario) a 4 xogadores.
 *  1.3 Poderase elixir a dificultade entre tres posibilidades:
 *      Fácil - xerarase un taboleiro de 11x11 casillas,
 *      Normal - xerarase un taboleiro de 17x17 casillas,
 *      Difícil - xerarase un taboleiro de 21x21 casillas.
 *  1.4 Personalización dos nomes dos distintos xogadores.
 *  1.7 Ao final de cada partida amósase unha taboa de resultados en lugar 
 *     dunha comparación literal. Na taboa quedarán ordenados os distintos xogadores
 *     segundo os puntos que conseguisen durante o xogo. Isto permite que un xogador 
 *     que se rindeu antes poida gañar igualmente por ter empregado mellores palabras.
 * 
 * 
 * 
 * 2. INSTRUCCIÓNS
 * 
 * 2.1 SOBRE TURNOS E RONDAS
 * Unha vez configurado o xogo lánzase o taboleiro coa configuración correspondente,
 * o cal pode visualizarse a través da terminal de Java.
 * Os xogadores seguen a orden natural na que se inseriron no proceso de creación de
 * xogadores. O xogo está organizado por rondas, onde cada ronda supón a participación
 * de todos os xogadores unha vez. Si un xogador insire palabra ou pasa turno, inicia o turno
 * do seguinte xogador. O xogador que pasou turno volverá a xogar na seguinte ronda.
 * 
 * 
 * 2.2 SOBRE A INSERCIÓN DE DATOS
 * O primeiro xogador pode intrducir calquera palabra formable coas letras da súa man,
 * que se ubicará automáticamente no centro do taboleiro sin necesidade de indicar o
 * lugar de inserción. Esta palabra constituirá o lugar a partir do cal os demáis xogadores 
 * poidan inserir as súas palabras coa condición necesaria de que intersequen polo menos
 * unha letra dunha palabra xa presente no taboleiro.
 * A partir do primeiro xogador, os demáis xogadores deberán introducir, tras a súa palabra,
 * un conxunto de 3 datos separados por comas: dato1,dato2,dato3
 * onde 
 *  *dato1: indica a fila de insercción da primeira letra da palabra.
 *  *dato2: indica a columna de inserción da primeira letra da palabra.
 *  *dato3: indica a orientación da palabra:
 *              h ou H - inican inserción horizontal.
 *              v ou V - indican inserción vertical.
 * Adicionalmente, o xogador poderá pasas turno introducindo un -1 en lugar da palabra.
 * Ademáis do obvio, pasar turno renova unha letra aleatoria da man que non sexa comodín, o
 * que resulta un recurso útil si non eres capaz de idear algunha palabra coas letras actuais.
 * 
 * 
 * 2.3 SOBRE OS PREMIOS
 * No taboleiro aparecen diseminados de manera aleatoria una serie de bonificadores ou premios,
 * que actúan como multiplicadores na puntuación xerada polas letras ou palabras inserdias
 * que caian sobre éstos.
 * 
 * 
 * 2.4 SOBRE OS COMODINS
 * Un comodín ven representado co picograma: #
 * Os comodíns poden actuar como calquer letra do abecedario que precisemos, pero coa desvantaxe de 
 * que sempre puntuará con 0 puntos.
 * Os comodíns empregaranse automáticamente cando se precise a letra e non contemos ocn ninguna na man,
 * pero sempre como último recurso.
 * Ao inserir un comodin, este transfórmase na letra precisada e insírese no taboleiro como unha letra máis.
 * 
 * 
 * 2.5 SOBRE AS PUNTUACIÓNS
 * Xeraranse puntos segundo as letras das palabras introducidas ao longo da partida. 
 * Xerarase a puntuación especial [SCRABBLE +50] si o xogador gasta todas as letras da man
 * nun único turno.
 * Poderanse consultar as puntuacións durante o xogo en tres lugares distintos:
 *      *Como información de logg tras introducir a palabra; amosarase un pequeno informe
 *       da puntuación conseguida coas letras da palabra, e dos multiplicadores empregados.
 *      *Como puntuación acumulada, no perfil de xogo; a carón do nome, sobre a lapela de letras
 *       das que dispón o xogador por turno, poderase consultar a súa puntuación actual.
 *      *Ao final da partida, nun taboleiro resumo que ordena aos xogadores por puntuación, 
 *       amosando as posicións no podio.
 * 
 * 
 * @author Manuel Landín Gómez
 * @author Iago Leirós Pérez
 * 
 */
public class Menu {

    private static final Scanner SC = new Scanner(System.in);
    private static boolean control;
    private static final String INSTRUCTIONS = "O xogo é unha versión adaptada do Scrabble á terminal de Java segundo\n" +
"  as especificacións propostas do proxecto.\n" +
"  \n" +
"  \n" +
"  1. INSTRUCCIÓNS\n" +
"  \n" +
"  1.1 SOBRE TURNOS E RONDAS\n" +
"  Unha vez configurado o xogo lánzase o taboleiro coa configuración correspondente,\n" +
"  o cal pode visualizarse a través da terminal de Java.\n" +
"  Os xogadores seguen a orden natural na que se inseriron no proceso de creación de\n" +
"  xogadores. O xogo está organizado por rondas, onde cada ronda supón a participación\n" +
"  de todos os xogadores unha vez. Si un xogador insire palabra ou pasa turno, inicia o turno\n" +
"  do seguinte xogador. O xogador que pasou turno volverá a xogar na seguinte ronda.\n" +
"  \n" +
"  \n" +
"  1.2 SOBRE A INSERCIÓN DE DATOS\n" +
"  O primeiro xogador pode intrducir calquera palabra formable coas letras da súa man,\n" +
"  que se ubicará automáticamente no centro do taboleiro sin necesidade de indicar o\n" +
"  lugar de inserción. Esta palabra constituirá o lugar a partir do cal os demáis xogadores \n" +
"  poidan inserir as súas palabras coa condición necesaria de que intersequen polo menos\n" +
"  unha letra dunha palabra xa presente no taboleiro.\n" +
"  A partir do primeiro xogador, os demáis xogadores deberán introducir, tras a súa palabra,\n" +
"  un conxunto de 3 datos separados por comas: dato1,dato2,dato3\n" +
"  onde \n" +
"   *dato1: indica a fila de insercción da primeira letra da palabra.\n" +
"   *dato2: indica a columna de inserción da primeira letra da palabra.\n" +
"   *dato3: indica a orientación da palabra:\n" +
"               h ou H - inican inserción horizontal.\n" +
"               v ou V - indican inserción vertical.\n" +
"  Adicionalmente, o xogador poderá pasas turno introducindo un -1 en lugar da palabra.\n" +
"  Ademáis do obvio, pasar turno renova unha letra aleatoria da man que non sexa comodín, o\n" +
"  que resulta un recurso útil si non eres capaz de idear algunha palabra coas letras actuais.\n" +
"  \n" +
"  \n" +
"  1.3 SOBRE OS PREMIOS\n" +
"  No taboleiro aparecen diseminados de manera aleatoria una serie de bonificadores ou premios,\n" +
"  que actúan como multiplicadores na puntuación xerada polas letras ou palabras inserdias\n" +
"  que caian sobre éstos.\n" +
"  \n" +
"  \n" +
"  1.4 SOBRE OS COMODINS\n" +
"  Un comodín ven representado co picograma: #\n" +
"  Os comodíns poden actuar como calquer letra do abecedario que precisemos, pero coa desvantaxe de \n" +
"  que sempre puntuará con 0 puntos.\n" +
"  Os comodíns empregaranse automáticamente cando se precise a letra e non contemos ocn ninguna na man,\n" +
"  pero sempre como último recurso.\n" +
"  Ao inserir un comodin, este transfórmase na letra precisada e insírese no taboleiro como unha letra máis.\n" +
"  \n" +
"  \n" +
"  1.5 SOBRE AS PUNTUACIÓNS\n" +
"  Xeraranse puntos segundo as letras das palabras introducidas ao longo da partida. \n" +
"  Xerarase a puntuación especial [SCRABBLE +50] si o xogador gasta todas as letras da man\n" +
"  nun único turno.\n" +
"  Poderanse consultar as puntuacións durante o xogo en tres lugares distintos:\n" +
"       *Como información de logg tras introducir a palabra; amosarase un pequeno informe\n" +
"        da puntuación conseguida coas letras da palabra, e dos multiplicadores empregados.\n" +
"       *Como puntuación acumulada, no perfil de xogo; a carón do nome, sobre a lapela de letras\n" +
"        das que dispón o xogador por turno, poderase consultar a súa puntuación actual.\n" +
"       *Ao final da partida, nun taboleiro resumo que ordena aos xogadores por puntuación, \n" +
"        amosando as posicións no podio.";
    private static final String CREDITS =
"  1. MELLORAS\n" +
"  Incorpóranse as seguintes melloras sobre o xogo base:\n" +
"   1.1 Amosarase un menú inicial coas opcións que ten o xogador cando se lanza o programa.\n" +
"   1.2 Poderase elixir o número de xogadores que comporán o xogo de 1 (solitario) a 4 xogadores.\n" +
"   1.3 Poderase elixir a dificultade entre tres posibilidades:\n" +
"       Fácil - xerarase un taboleiro de 11x11 casillas,\n" +
"       Normal - xerarase un taboleiro de 17x17 casillas,\n" +
"       Difícil - xerarase un taboleiro de 21x21 casillas.\n" +
"   1.4 Personalización dos nomes dos distintos xogadores.\n" +
"   1.7 Ao final de cada partida amósase unha taboa de resultados en lugar \n" +
"      dunha comparación literal. Na taboa quedarán ordenados os distintos xogadores\n" +
"      segundo os puntos que conseguisen durante o xogo. Isto permite que un xogador \n" +
"      que se rindeu antes poida gañar igualmente por ter empregado mellores palabras.\n" +
"  \n";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        amosarMenu();
    }

    private static void amosarMenu() {

        do {
            System.out.println("\n                    _     _     _      \n"
                    + "                   | |   | |   | |     \n"
                    + " ___  ___ _ __ __ _| |__ | |__ | | ___ \n"
                    + "/ __|/ __| '__/ _` | '_ \\| '_ \\| |/ _ \\\n"
                    + "\\__ \\ (__| | | (_| | |_) | |_) | |  __/\n"
                    + "|___/\\___|_|  \\__,_|_.__/|_.__/|_|\\___|" + Color.RESET);

            System.out.println("\n\n========================================");

            System.out.println("\t\u2460\tNova Partida");

            System.out.println("\t\u2461\tInstruccións");

            System.out.println("\t\u2462\tMelloras");

            System.out.println("\t\u2463\tSaír");

            System.out.println("========================================\n");

            switch (Lector.entero(1, 5)) {
                case 1:
                    System.out.println(Color.FONDO_GRIS + "Configuración do novo xogo" + Color.RESET);
                    System.out.println("\u2460\t \u25fb Fácil (11x11)\n\u2461\t \u25e7 Normal (17x17)\n\u2462\t \u25fc Difícil (21x21)");
                    int dificultade = Lector.entero(1, 4);

                    switch (dificultade) {
                        case 1:
                            new Tablero(11, 11);
                            break;
                        case 2:
                            new Tablero(17, 17);
                            break;
                        case 3:
                            new Tablero(21, 21);
                            break;
                    }

                    control = true;
                    break;
                case 2:
                    System.out.println(INSTRUCTIONS);
                    System.out.println("\nPremer \u001B[35mENTER\u001B[30m para voltar ao menú.");
                    SC.nextLine();
                    control = true;
                    break;
                case 3:
                    System.out.println(CREDITS);
                    System.out.println("\nPremer \u001B[35mENTER\u001B[30m para voltar ao menú.");
                    SC.nextLine();
                    control = true;
                    break;
                case 4:
                    System.out.println("\nSaíndo...");
                    control = false;
                    break;
                default:
                    break;
            }
        } while (control);
    }

}
