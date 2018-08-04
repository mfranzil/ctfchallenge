package ctfchallenge;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class TeamList extends SimpleListProperty<Team> {
    /**
     * Una ObservableList aggiornata man mano con le squadre della partita. Viene osservata dalla Scoreboard per
     * aggiornarne i contenuti.
     */

    public TeamList() {
        super(FXCollections.observableArrayList());
    }

    /**
     * Metodo che prende in entrata un nome e ritorna la squadra corrispondente al nome.
     *
     * @param name Il nome della squadra da cercare
     * @return Una Team che per prima ha un match con il valore cercato.
     */
    public Team getSquadraFromName(String name) {
        Team res = null;
        for (Team i : this) {
            if (name.equals(i.getTeamName())) {
                res = i;
                break;
            }
        }
        return res;
    }

    /**
     * Metodo per ottenere, all'interno della lista delle squadre, il Team con punteggio maggiore.
     *
     * @return Un ArrayList contenente 1 o più squadre con il punteggio maggiore.
     */
    public ArrayList<Team> getLeader() {
        Collections.sort(this);
        int punteggio_temp = this.get(0).getScore();

        ArrayList<Team> temp = new ArrayList<>();
        for (Team i : this) {
            if (i.getScore() < punteggio_temp) {
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
    public void processVictory(TextArea txt) {
        ArrayList<Team> winners = getLeader();
        switch (winners.size()) {
            case 0:
                txt.appendText("Sembra che non abbia vinto nessuno....guarda la classifica per ottenere il vincitore.\n");
                break;
            case 1:
                txt.appendText("PARTITA FINITA!\nVince la squadra " + winners.get(0));
                break;
            default:
                txt.appendText("PARTITA FINITA\nVincono le squadre: \n");
                winners.forEach((Team i) -> txt.appendText(i.getTeamName() + "\n"));
                break;
        }
    }
}
