package ctfchallenge.ui;

import ctfchallenge.assets.AccentParser;
import ctfchallenge.assets.TeamList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public final class PointAssigner extends VBox {

    public PointAssigner() {
        setBackground(new Background(new BackgroundFill(AccentParser.getLightAccentColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(15));
    }

    public void setPointAssigner(@NotNull TeamList teamList) {
        teamList.forEach(i -> {
            AssignerItem ai = new AssignerItem(i);
            getChildren().add(ai);
        });
    }

    public void sendResults() {
        getChildren().forEach(i -> ((AssignerItem) i).sendResults());
    }
}
