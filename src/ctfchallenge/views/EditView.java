package ctfchallenge.views;

import ctfchallenge.Scoreboard;
import ctfchallenge.Team;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class EditView extends Stage {

    /**
     * Questa finestra viene mostrata in caso di aggiunta o modifica di una squadra
     *
     * @param txt        La finestra di log del programma.
     * @param scoreboard La finestea della scoreboard.
     * @param team        La squadra che verrà modificata.
     * @param isEdit     Metodo evocato in edit mode o addition mode (con punteggi bloccati)
     */
    public EditView(TextArea txt, Scoreboard scoreboard, Team team, boolean isEdit) {
        GridPane editPane = new GridPane();
        Scene editScene = new Scene(editPane);

        Button send = new Button("Invia");

        TextField nomeField = new TextField(team.getTeamName());
        TextField m1Field = new TextField(team.getMember1());
        TextField m2Field = new TextField(team.getMember2());
        TextField ptField = new TextField(String.valueOf(team.getScore()));

        if (!isEdit) {
            ptField.setEditable(false);
            ptField.setDisable(true);
        }

        Text nomeText = new Text("Nome");
        Text m1Text = new Text("Membro 1");
        Text m2Text = new Text("Membro 2");
        Text ptText = new Text("Punteggio");

        editPane.add(nomeText, 0, 0);
        editPane.add(m1Text, 0, 1);
        editPane.add(m2Text, 0, 2);
        editPane.add(ptText, 0, 3);
        editPane.add(nomeField, 1, 0);
        editPane.add(m1Field, 1, 1);
        editPane.add(m2Field, 1, 2);
        editPane.add(ptField, 1, 3);
        editPane.add(send, 1, 4);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);
        editPane.getColumnConstraints().addAll(c1, c2);

        editPane.setHgap(8);
        editPane.setVgap(8);
        editPane.setPadding(new Insets(15, 12, 15, 12));

        GridPane.setHalignment(send, HPos.RIGHT);

        send.setOnAction((ActionEvent event) -> {
            team.setTeamName(nomeField.getText());
            team.setMember1(m1Field.getText());
            team.setMember2(m2Field.getText());
            team.setScore(Integer.parseInt(ptField.getText()));
            this.close();
            scoreboard.refresh();
            txt.appendText("Team con nome " + team.getTeamName() + (isEdit ? " aggiornato:" : " aggiunto:")
                    + "\nMembri: " + team.getMember1() + ", " + team.getMember2()
                    + "\nPunteggio: " + team.getScore() + "\n");
        });

        setScene(editScene);
        setTitle((isEdit ? "Modifica squadra..." : "Aggiungi squadra..."));
        getIcons().add(new Image("file:logo.png"));
    }

}
