package ctfchallenge.ui;

import ctfchallenge.assets.AccentParser;
import ctfchallenge.assets.Common;
import ctfchallenge.assets.TeamList;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * This class is a wrapper for single AssignerTeam instances.
 *
 * @author Matteo Franzil
 * @version 20181108v1
 */
public final class AssignerTable extends VBox {

    /**
     * Standard constructor. Sets the background with the light accent color (or light gray).
     */
    public AssignerTable() {
        setBackground(new Background(new BackgroundFill(AccentParser.getLightAccentColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(15));
    }

    /**
     * Recursively adds AssignerItems from a single TeamList.
     *
     * @param teamList The ObservableList of teams needed for the initialization.
     */
    public void setAssignerTable(TeamList teamList) {
        HBox header = new HBox();
        header.getChildren().add(new Text(" "));
        for (int i = 0; i < Common.MAX_ROUNDS; i++) {
            header.getChildren().add(new Text(String.valueOf(i + 1)));
        }

        header.setPadding(new Insets(15));
        header.setSpacing(80);
        getChildren().add(header);

        teamList.forEach(i -> {
            AssignerTeam ai = new AssignerTeam(i);
            getChildren().add(ai);
        });
    }

    /**
     * Recursively dismantles AssignerItems.
     */
    public void dismantle() {
        getChildren().forEach(i -> {
            if (i instanceof AssignerTeam) {
                ((AssignerTeam) i).dismantle();
            }
        });
    }
}
