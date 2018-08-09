package ctfchallenge.ui;

import ctfchallenge.Team;
import ctfchallenge.assets.Common;
import ctfchallenge.assets.Logging;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import static ctfchallenge.assets.Common.currentEs;

public final class AssignerItem extends HBox {

    private Team team;
    private ToggleGroup tg;
    private ComboBox<String> posizione;

    public AssignerItem(Team team) {
        this.team = team;

        RadioButton completed = new RadioButton("Completato");
        RadioButton notCompleted = new RadioButton("Non completato");
        tg = new ToggleGroup();
        posizione = new ComboBox<>();

        Integer min = Math.min(Common.MAX_TEAMS_BONUS, Common.teamNumber);
        for (int i = 0; i < min; i++) {
            posizione.getItems().add(String.valueOf(i + 1));
        }
        posizione.getItems().add("Fuori");
        posizione.setValue("Fuori");
        posizione.setDisable(true);

        completed.setToggleGroup(tg);
        notCompleted.setToggleGroup(tg);
        notCompleted.setSelected(true);

        completed.setOnAction(e -> posizione.setDisable(false));
        notCompleted.setOnAction(e -> {
            posizione.setDisable(true);
            posizione.setValue("Fuori");
        });

        getChildren().addAll(new Label(team.getTeamName()) {{
            setMinWidth(250);
        }}, completed, notCompleted, new Label("") {{
            setMinWidth(70);
        }}, new Text("Posizione:"), posizione);

        setPadding(new Insets(15));
        setSpacing(20);
    }

    public void sendResults() {
        Integer punteggio = Common.punteggioEs(currentEs);

        if (((Labeled) tg.getSelectedToggle()).getText().equals("Completato")) {
            team.incrementScore(punteggio);
            Logging.info("La squadra " + team.getTeamName() + " ottiene " + punteggio + " punti");
        }

        Integer min = Math.min(Common.MAX_TEAMS_BONUS, Common.teamNumber);
        try {
            Integer punteggioPosizione = Integer.parseInt(posizione.getValue());
            team.setScore(team.getScore() + min - punteggioPosizione + 1);
            Logging.info("La squadra " + team.getTeamName()
                    + " ottiene " + (min - punteggioPosizione + 1) + " punti bonus");
        } catch (NumberFormatException ex) {
            Logging.info("Nessun punto bonus per " + team.getTeamName());
        }
        posizione.getSelectionModel().clearSelection();
    }
}
