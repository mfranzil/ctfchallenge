package ctfchallenge;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


/**
 * @since 07/05/2018
 * @version 1.0
 * @author Matteo Franzil
 */
public class RadioButtons extends GridPane {

    private final ArrayList<ToggleGroup> radio_btn;
    private final ArrayList<Text> radio_text;
    private final ArrayList<RadioButton> vinto;
    private final ArrayList<RadioButton> perso;

    /**
     * Costruttore standard. La classe estende un GridPane e contiene 4 ArrayList con i figli.
     */
    public RadioButtons() {
        radio_btn = new ArrayList<>();
        radio_text = new ArrayList<>();
        vinto = new ArrayList<>();
        perso = new ArrayList<>();
        
        setStyle("-fx-background-color: #cce5ff;");
        setHgap(8);
        setVgap(8);
        setPadding(new Insets(15, 12, 15, 12));  
    }

    /**
     * Metodo che fa apparire sul Pane dato i bottoni inizializzati in precedenza.
     * @param gridView Il Pane in cui aggiungere i bottoni
     * @param squadre Una ObservableList di Squadre
     */
    public void setRadioButtons(Pane gridView, ObservableList<Squadra> squadre) {
        int media = (int) Math.floor(squadre.size() / 2);
        for (int i = 0; i < squadre.size(); i++) {
            Text radio_text_tmp = new Text(squadre.get(i).getNomesquadra());
                        
            RadioButton vinto_tmp = new RadioButton("Completato");
            RadioButton perso_tmp = new RadioButton("Non completato");
            ToggleGroup radio_btn_tmp = new ToggleGroup();
            vinto_tmp.setToggleGroup(radio_btn_tmp);
            perso_tmp.setToggleGroup(radio_btn_tmp);
            perso_tmp.setSelected(true);

            if (i < media) {
                CTFChallenge.setColumnRowIndex(radio_text_tmp, 0, 0 + (3 * i));
                CTFChallenge.setColumnRowIndex(vinto_tmp, 0, 1 + (3 * i));
                CTFChallenge.setColumnRowIndex(perso_tmp, 0, 2 + (3 * i));
            } else {
                CTFChallenge.setColumnRowIndex(radio_text_tmp, 3, 0 + (3 * (i - media)));
                CTFChallenge.setColumnRowIndex(vinto_tmp, 3, 1 + (3 * (i - media)));
                CTFChallenge.setColumnRowIndex(perso_tmp, 3, 2 + (3 * (i - media)));
            }

            vinto.add(vinto_tmp);
            perso.add(perso_tmp);
            radio_btn.add(radio_btn_tmp);
            radio_text.add(radio_text_tmp);
            gridView.getChildren().addAll(vinto_tmp, perso_tmp, radio_text_tmp);
        }
    }

    /**
     * Metodo che preleva lo stato dei bottoni e li aggiunge alle squadre, con un punteggio prefissato.
     * @param txt La finestra di log del programma
     * @param squadre Una ObservableList di squadre
     * @param punteggio Il punteggio da attribuire in caso di esercizio completato
     */
    public void sendResults(TextArea txt, ObservableList<Squadra> squadre, int punteggio) {
        for (int i = 0; i < squadre.size(); i++) {
            String label = ((Labeled) radio_btn.get(i).getSelectedToggle()).getText();
            Squadra current = squadre.get(i);
            if (label.equals("Completato")) {
                current.incrementPunteggio(punteggio);
                txt.appendText("La squadra " + current.getNomesquadra() + " ottiene " + punteggio + " punti\n");
            }
        }
    }
}
