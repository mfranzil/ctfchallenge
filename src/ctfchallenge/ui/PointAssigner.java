package ctfchallenge.ui;

import ctfchallenge.assets.AccentParser;
import ctfchallenge.assets.TeamList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;

/**
 * This class is a wrapper for single AssignerItem instances.
 *
 * @author Matteo Franzil
 * @version 1.2
 */
public final class PointAssigner extends VBox {

    /**
     * Standard constructor. Sets the background with the light accent color (or light gray).
     */
    public PointAssigner() {
        setBackground(new Background(new BackgroundFill(AccentParser.getLightAccentColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(15));
    }

    /**
     * Recursively adds AssignerItems from a single TeamList.
     *
     * @param teamList The ObservableList of teams needed for the initialization.
     */
    public void setPointAssigner(TeamList teamList) {
        teamList.forEach(i -> {
            AssignerItem ai = new AssignerItem(i);
            getChildren().add(ai);
        });
    }

    /**
     * Calls sendResults on each AssignerItem instance.
     */
    public void sendResults() {
        getChildren().forEach(i -> ((AssignerItem) i).sendResults());
    }
}
