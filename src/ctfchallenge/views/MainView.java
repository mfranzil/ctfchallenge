package ctfchallenge.views;

import ctfchallenge.assets.Logging;
import ctfchallenge.assets.TeamList;
import ctfchallenge.ui.AssignerTable;
import ctfchallenge.ui.Toolbar;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 20181105v2
 */
public class MainView extends Stage {
    public MainView(Logging logWindow, TeamList teamList) {
        BorderPane mainPane = new BorderPane();
        Scene mainScene = new Scene(mainPane);

        AssignerTable assignerTable = new AssignerTable();

        Toolbar toolbar = new Toolbar(assignerTable, teamList);

        mainPane.setTop(toolbar);
        mainPane.setLeft(logWindow);
        mainPane.setCenter(assignerTable);

        setScene(mainScene);
        initGUI();
    }

    private void initGUI() {
        setMaximized(false);
        setWidth(1100);
        setHeight(600);
        setTitle("CTFChallenge");

        setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you really want to quit?",
                    ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> Platform.exit());
        });

        // Icona dell'applicazione
        getIcons().add(new Image("file:logo.png"));
    }
}
