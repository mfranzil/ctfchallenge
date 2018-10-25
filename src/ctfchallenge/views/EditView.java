package ctfchallenge.views;

import ctfchallenge.Team;
import ctfchallenge.assets.Logging;
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
 * @version 1.3.1
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

        TextField name = new TextField(team.getName());
        TextField player1 = new TextField(team.getPlayer1());
        TextField player2 = new TextField(team.getPlayer2());
        TextField score = new TextField(String.valueOf(team.getScore()));

        if (!isEdit) {
            score.setEditable(false);
            score.setDisable(true);
        }

        editPane.add(new Text("Name"), 0, 0);
        editPane.add(new Text("Player 1"), 0, 1);
        editPane.add(new Text("Player 2"), 0, 2);
        editPane.add(new Text("Score"), 0, 3);
        editPane.add(name, 1, 0);
        editPane.add(player1, 1, 1);
        editPane.add(player2, 1, 2);
        editPane.add(score, 1, 3);
        editPane.add(send, 1, 4);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);
        editPane.getColumnConstraints().addAll(c1, c2);

        editPane.setHgap(8);
        editPane.setVgap(8);
        editPane.setPadding(new Insets(15));

        GridPane.setHalignment(send, HPos.RIGHT);

        send.setOnAction((ActionEvent event) -> {
            try {
                team.setName(name.getText());
                team.setPlayer1(player1.getText());
                team.setPlayer2(player2.getText());
                team.setScore(Integer.parseInt(score.getText()));
                close();
                //Scoreboard.refreshScoreboard();
                Logging.info("Team successfully" + (isEdit ? " updated:" : " added:")
                        + "\nName: " + team.getName()
                        + "\nMembers: " + team.getPlayer1() + ", " + team.getPlayer2() +
                        (isEdit ? "\nScore: " + team.getScore() : ""));
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Please enter a valid number for the score.", ButtonType.OK)
                        .showAndWait();
            }
        });

        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner.getScene().getWindow());
        setScene(editScene);
        setResizable(false);
        setTitle((isEdit ? "Edit team..." : "Add team..."));
        getIcons().add(new Image("file:logo.png"));
    }

}
