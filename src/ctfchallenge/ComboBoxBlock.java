package ctfchallenge;

import ctfchallenge.assets.Common;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;


/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class ComboBoxBlock extends GridPane {

    private final ArrayList<Text> cbox_text;
    private final ArrayList<ComboBox<String>> cbox;

    /**
     * Classe che estende un GridPane e contiene al suo interno delle ComboBox
     * per decidere i primi, secondi, etc... classificati.
     */
    public ComboBoxBlock() {
        cbox_text = new ArrayList<>();
        cbox = new ArrayList<>();

        setStyle("-fx-background-color: #cce5ff;");
        setPadding(new Insets(15, 12, 15, 12));
        setHgap(8);
        setVgap(8);
        getColumnConstraints().add(new ColumnConstraints(250));
    }

    /**
     * Metodo che mostra le ComboBox inizializzate in precedenza.
     *
     * @param squadreList Una ObservableList di Squadre
     */
    public void setComboBox(ObservableList<Team> squadreList) {
        for (int i = 0; i < Common.MAX_TEAMS_BONUS; i++) {
            ComboBox<String> cbox_temp = new ComboBox<>();
            Text cbox_text_temp = new Text(Common.intToText(i + 1) + " classificato");

            add(cbox_text_temp, 0, (2 * i));
            add(cbox_temp, 0, 1 + (2 * i));

            squadreList.forEach((temp) -> cbox_temp.getItems().add(temp.getTeamName()));

            cbox.add(cbox_temp);
            cbox_text.add(cbox_text_temp);
        }
    }

    /**
     * Metodo che prende le squadre selezionate nel ComboBox e ne assegna un punteggio prefissato.
     *
     * @param txt         La finestra di log del programma.
     * @param teamList Una ObservableList di Squadre
     */
    public void sendResults(TextArea txt, TeamList teamList) {
        for (int i = 0; i < cbox.size(); i++) {
            String object_name = (cbox.get(i).getValue());
            if (object_name != null) {
                Team temp = teamList.getSquadraFromName(object_name);
                temp.setScore(temp.getScore() + Common.MAX_TEAMS_BONUS - i);
                txt.appendText("La squadra " + temp.getTeamName()
                        + " ottiene " + (Common.MAX_TEAMS_BONUS - i) + " punti bonus\n");
            }
        }
        clearAll();
    }

    private void clearAll() {
        cbox.forEach((i) -> i.getSelectionModel().clearSelection());
    }
}
