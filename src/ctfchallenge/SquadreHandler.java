package ctfchallenge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class SquadreHandler {

    /**
     * Una ObservableList aggiornata man mano con le squadre della partita. Viene osservata dalla Scoreboard per
     * aggiornarne i contenuti.
     */
    public final ObservableList<Squadra> squadreList = FXCollections.observableArrayList();

    /**
     * Metodo per ottenere, all'interno della lista delle squadre, la Squadra con punteggio maggiore.
     *
     * @return Un ArrayList contenente 1 o più squadre con il punteggio maggiore.
     */
    public ArrayList<Squadra> getLeader() {
        Collections.sort(squadreList);
        int punteggio_temp = squadreList.get(0).getPunteggio();

        ArrayList<Squadra> temp = new ArrayList<>();
        for (Squadra i : squadreList) {
            if (i.getPunteggio() < punteggio_temp) {
                break;
            } else {
                temp.add(i);
            }
        }
        return temp;
    }

    /**
     * Metodo per terminare la partita; si comporta in maniera diversa a seconda del numero dei vincitori.
     *
     * @param txt La finestra di log del programma
     */
    public void victoryHandler(TextArea txt) {
        ArrayList<Squadra> winners = getLeader();
        switch (winners.size()) {
            case 0:
                txt.appendText("Sembra che non abbia vinto nessuno....guarda la classifica per ottenere il vincitore.\n");
                break;
            case 1:
                txt.appendText("PARTITA FINITA!\nVince la squadra " + winners.get(0));
                break;
            default:
                txt.appendText("PARTITA FINITA\nVincono le squadre: \n");
                winners.forEach((Squadra i) -> txt.appendText(i.getNomesquadra() + "\n"));
                break;
        }
    }

}
