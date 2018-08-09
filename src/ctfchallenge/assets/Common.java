package ctfchallenge.assets;

import org.jetbrains.annotations.Nullable;

public class Common {
    /**
     *  Il numero di squadre che hanno diritto al bonus durante la partita.
     */
    public static final int MAX_TEAMS_BONUS = 5;
    /**
     *  Il numero totale di esercizi che vengono svolti.
     */
    public static final int MAX_EXERCISES = 5;
    /**
     *  Il numero dell'esercizio corrente-
     */
    public static int currentEs = 0;
    
    public static int teamNumber;

    /**
     * Funzione ausiliaria per assegnare i punti agli esercizi. Sono supportati
     * fino a 5 esercizi.
     *
     * @param i L'indice dell'esercizio di cui ottenere il punteggio.
     * @return Il punteggio dell'esercizio desiderato.
     */
    public static int punteggioEs(int i) {
        int res;
        switch (i) {
            case 1:
                res = 5;
                break;
            case 2:
                res = 10;
                break;
            case 3:
                res = 15;
                break;
            case 4:
                res = 25;
                break;
            case 5:
                res = 25;
                break;
            default:
                res = -1;
                break;
        }
        return res;
    }

    /**
     * Funzione ausiliaria statica che trasforma un numero cardinale nella sua
     * versione ordinale scritta in String.
     *
     * @param i Il numero da 1 a 5 da trasformare in String.
     * @return Una String con la prima lettera maiuscola, rappresentante
     * l'input.
     */
    @Nullable
    public static String intToText(int i) {
        String res;
        switch (i) {
            case 1:
                res = "Primo";
                break;
            case 2:
                res = "Secondo";
                break;
            case 3:
                res = "Terzo";
                break;
            case 4:
                res = "Quarto";
                break;
            case 5:
                res = "Quinto";
                break;
            default:
                res = null;
                break;
        }
        return res;
    }
}
