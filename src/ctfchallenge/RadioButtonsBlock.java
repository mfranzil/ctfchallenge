package ctfchallenge;

import ctfchallenge.assets.AccentParser;
import ctfchallenge.assets.Logging;
import javafx.geometry.Insets;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;


/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class RadioButtonsBlock extends GridPane {

    private final ArrayList<ToggleGroup> radio_btn;
    private final ArrayList<Text> radio_text;
    private final ArrayList<RadioButton> vinto;
    private final ArrayList<RadioButton> perso;

    /**
     * Costruttore standard. La classe estende un GridPane e contiene 4 ArrayList con i figli.
     */
    public RadioButtonsBlock() {
        radio_btn = new ArrayList<>();
        radio_text = new ArrayList<>();
        vinto = new ArrayList<>();
        perso = new ArrayList<>();

        setBackground(new Background(new BackgroundFill(AccentParser.getLightAccentColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));
        setHgap(8);
        setVgap(8);
        setPadding(new Insets(15, 12, 15, 12));
    }

    /**
     * Metodo che fa apparire sul Pane dato i bottoni inizializzati in precedenza.
     *
     * @param gridView Il Pane in cui aggiungere i bottoni
     * @param squadre  Una ObservableList di Squadre
     */
    public void setRadioButtons(GridPane gridView, TeamList squadre) {
        int media = (int) Math.floor(squadre.size() / 2);
        for (int i = 0; i < squadre.size(); i++) {
            Text radio_text_tmp = new Text(squadre.get(i).getTeamName());

            RadioButton vinto_tmp = new RadioButton("Completato");
            RadioButton perso_tmp = new RadioButton("Non completato");
            ToggleGroup radio_btn_tmp = new ToggleGroup();
            vinto_tmp.setToggleGroup(radio_btn_tmp);
            perso_tmp.setToggleGroup(radio_btn_tmp);
            perso_tmp.setSelected(true);

            if (i < media) {
                gridView.add(radio_text_tmp, 0, (3 * i));
                gridView.add(vinto_tmp, 0, 1 + (3 * i));
                gridView.add(perso_tmp, 0, 2 + (3 * i));
            } else {
                gridView.add(radio_text_tmp, 3, (3 * (i - media)));
                gridView.add(vinto_tmp, 3, 1 + (3 * (i - media)));
                gridView.add(perso_tmp, 3, 2 + (3 * (i - media)));
            }

            vinto.add(vinto_tmp);
            perso.add(perso_tmp);
            radio_btn.add(radio_btn_tmp);
            radio_text.add(radio_text_tmp);
        }
    }

    /**
     * Metodo che preleva lo stato dei bottoni e li aggiunge alle squadre, con un punteggio prefissato.
     *
     * @param squadre   Una ObservableList di squadre
     * @param punteggio Il punteggio da attribuire in caso di esercizio completato
     */
    public void sendResults(TeamList squadre, int punteggio) {
        for (int i = 0; i < squadre.size(); i++) {
            String label = ((Labeled) radio_btn.get(i).getSelectedToggle()).getText();
            Team current = squadre.get(i);
            if (label.equals("Completato")) {
                current.incrementScore(punteggio);
                Logging.info("La squadra " + current.getTeamName() + " ottiene " + punteggio + " punti");
            }
        }
    }
}
