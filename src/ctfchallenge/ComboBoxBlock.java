package ctfchallenge;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;


/**
 * @since 07/05/2018
 * @version 1.0
 * @author Matteo Franzil
 */
public class ComboBoxBlock extends GridPane {

    private final ArrayList<Text> cbox_text;
    private final ArrayList<ComboBox<String>> cbox;

    /**
     *
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
     *
     * @param gridView
     * @param squadreHandler
     */
    public void setComboBox(Pane gridView, SquadreHandler squadreHandler) {
        for (int i = 0; i < CTFChallenge.MAX_TEAMS_BONUS; i++) {
            ComboBox<String> cbox_temp = new ComboBox<>();
            Text cbox_text_temp = new Text(CTFChallenge.intToText(i + 1) + " classificato");
            
            CTFChallenge.setColumnRowIndex(cbox_text_temp, 0, 0 + (2 * i));
            CTFChallenge.setColumnRowIndex(cbox_temp, 0, 1 + (2 * i));
            
            squadreHandler.squadreList.forEach((temp) -> {
                cbox_temp.getItems().add(temp.getNomesquadra());
            });
            
            cbox.add(cbox_temp);
            cbox_text.add(cbox_text_temp);
            gridView.getChildren().addAll(cbox_text.get(i), cbox.get(i));
        }
    }

    /**
     *
     * @param squadreHandler
     */
    public void sendResults(TextArea txt, SquadreHandler squadreHandler) {
        for (int i = 0; i < cbox.size(); i++) {
            String object_name = (cbox.get(i).getValue());
            if (object_name != null) {
                Squadra temp = Squadra.getSquadraFromName(object_name, squadreHandler.squadreList);
                temp.setPunteggio(temp.getPunteggio() + CTFChallenge.MAX_TEAMS_BONUS - i);
                txt.appendText("La squadra " + temp.getNomesquadra()
                        + " ottiene " + (CTFChallenge.MAX_TEAMS_BONUS - i) + " punti bonus\n");
            }
        }
        clearAll();
    }

    private void clearAll() {
        cbox.forEach((i) -> {
            i.getSelectionModel().clearSelection();
        });
    }
}
