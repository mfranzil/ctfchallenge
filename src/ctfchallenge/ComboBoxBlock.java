/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import static ctfchallenge.CTFChallenge.getSquadre;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author matte
 */
public class ComboBoxBlock {

    private final ArrayList<Text> cbox_text;
    private final ArrayList<ComboBox<String>> cbox;

    public final int MAX_TEAMS_BONUS = 5;

    /**
     *
     */
    public ComboBoxBlock() {
        cbox_text = new ArrayList<>();
        cbox = new ArrayList<>();
    }

    public void setComboBox(Pane gridView) {
        // Inizializzo i combobox e le scritte
        for (int i = 0; i < MAX_TEAMS_BONUS; i++) {
            ComboBox<String> cbox_temp = new ComboBox<>();
            Text cbox_text_temp = new Text(CTFChallenge.intToText(i + 1) + " classificato");
            
            CTFChallenge.setColumnRowIndex(cbox_text_temp, 10, 2 + (2 * i));
            CTFChallenge.setColumnRowIndex(cbox_temp, 10, 3 + (2 * i));
            
            for (Squadra temp : CTFChallenge.getSquadre()) {
                cbox.get(i).getItems().add(temp.getNomesquadra());
            }
            
            cbox.add(cbox_temp);
            cbox_text.add(cbox_text_temp);
            gridView.getChildren().addAll(cbox_text.get(i), cbox.get(i));
        }
    }

    public void sendResults() {
        for (int i = 0; i < cbox.size(); i++) {
            String object_name = (cbox.get(i).getValue());
            if (object_name != null) {
                Squadra temp = Squadra.getSquadraFromName(object_name, CTFChallenge.getSquadre());
                temp.setPunteggio(temp.getPunteggio() + 5 - i);
                CTFChallenge.getTxt().appendText("La squadra " + temp.getNomesquadra()
                        + " ottiene " + (5 - i) + " punti bonus\n");
            }
        }
        clearAll();
    }

    public void clearAll() {
        cbox.forEach((i) -> {
            i.getSelectionModel().clearSelection();
        });
    }

    /**
     *
     * @return
     */
    public ArrayList<ComboBox<String>> getCbox() {
        return cbox;
    }

}
