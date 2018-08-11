package ctfchallenge.views;

import ctfchallenge.Team;
import ctfchallenge.assets.Logging;
import ctfchallenge.ui.Scoreboard;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 1.2
 */
public class EditView extends Stage {

    /**
     * Window shown in case of addition or editing of Teams.
     *
     * @param team   The Team to be edited.
     * @param isEdit Start the window in edit mode or addition mode (cannot edit score)?
     * @param owner  The Node that called the window.
     */
    public EditView(Team team, boolean isEdit, Node owner) {
        GridPane editPane = new GridPane();
        Scene editScene = new Scene(editPane);

        Button send = new Button("Send");

        TextField nomeField = new TextField(team.getName());
        TextField m1Field = new TextField(team.getMember1());
        TextField m2Field = new TextField(team.getMember2());
        TextField ptField = new TextField(String.valueOf(team.getScore()));

        if (!isEdit) {
            ptField.setEditable(false);
            ptField.setDisable(true);
        }

        Text nomeText = new Text("Name");
        Text m1Text = new Text("Player 1");
        Text m2Text = new Text("Player 2");
        Text ptText = new Text("Score");

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
            try {
                team.setName(nomeField.getText());
                team.setMember1(m1Field.getText());
                team.setMember2(m2Field.getText());
                team.setScore(Integer.parseInt(ptField.getText()));
                close();
                Scoreboard.refreshScoreboard();
                Logging.info("Team successfully" + (isEdit ? " updated:" : " added:")
                        + "\nName: " + team.getName()
                        + "\nMembers: " + team.getMember1() + ", " + team.getMember2() +
                        (isEdit ? "\nScore: " + team.getScore() : ""));
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Formato del punteggio non corretto!", ButtonType.OK)
                        .showAndWait();
            }
        });

        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner.getScene().getWindow());
        setScene(editScene);
        setTitle((isEdit ? "Edit team..." : "Add team..."));
        getIcons().add(new Image("file:logo.png"));
    }

}
