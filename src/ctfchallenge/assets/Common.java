package ctfchallenge.assets;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Common {
    /**
     *
     */
    public static final int MAX_TEAMS_BONUS = 5;
    /**
     *
     */
    public static final int MAX_TEAMS = 10;
    /**
     *
     */
    public static final int MAX_EXERCISES = 5;
    /**
     *
     */
    public static int numeroes = 0;

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

    /**
     * Finestrella per chiedere una String customizzata senza grafica basata su
     * JavaFX.
     *
     * @param question La domanda da porre nella finestra
     * @return Una string contenente la risposta.
     */
    public static String optionDialog(String question) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(question);
        dialog.setTitle("CTF");
        dialog.setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
