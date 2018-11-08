package ctfchallenge.views;

import ctfchallenge.assets.Logging;
import ctfchallenge.assets.TeamList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RemoveView extends Stage {

    public RemoveView(TeamList teamList, Button removeTeam, Button editTeam) {
        VBox root = new VBox();
        Scene scene = new Scene(root);

        root.getChildren().add(new Text("Chooose the team to remove:"));
        root.setPadding(new Insets(16));
        root.setSpacing(16);

        teamList.forEach(team -> root.getChildren().add(new Button(team.getName()) {{
            setOnAction(e -> {
                Logging.info("Team with name " + team.getName() + " successfully removed");
                teamList.remove(team);
                root.getChildren().remove(this);

                if (teamList.size() <= 0) {
                    close();
                    removeTeam.setDisable(true);
                    editTeam.setDisable(true);
                }
            });
        }}));

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                close();
            }
        });

        initModality(Modality.APPLICATION_MODAL);
        initOwner(removeTeam.getScene().getWindow());
        setScene(scene);
        setResizable(false);
        setTitle("Choose the team");
        show();
    }
}
