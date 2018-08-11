package ctfchallenge.ui;


import ctfchallenge.Team;
import ctfchallenge.assets.Logging;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.NotNull;


/**
 * @author Matteo Franzil
 * @version 1.2
 */
public final class Scoreboard extends TableView<Team> {

    private static Scoreboard INSTANCE;

    @NotNull
    private final TableColumn<Team, String> name;
    @NotNull
    private final TableColumn<Team, TableColumn<Team, String>> players;
    @NotNull
    private final TableColumn<Team, String> member1;
    @NotNull
    private final TableColumn<Team, String> member2;
    @NotNull
    private final TableColumn<Team, String> score;

    private final int fontsizes[] = {6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 21, 24, 36, 48, 60, 72, 84, 96};
    private int pointer = 11; // Pt = 24

    /**
     * Standard constructor. The Scoreboard fully behaves like a TableView containing only Team instances.
     */
    @SuppressWarnings("unchecked")
    private Scoreboard() {
        name = new TableColumn<>("Team");
        players = new TableColumn<>("Players");
        member1 = new TableColumn<>("Player 1");
        member2 = new TableColumn<>("Player 2");
        score = new TableColumn<>("Score");

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        member1.setCellValueFactory(new PropertyValueFactory<>("member1"));
        member2.setCellValueFactory(new PropertyValueFactory<>("member2"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        name.prefWidthProperty().bind(widthProperty().divide(6.0));
        member1.prefWidthProperty().bind(widthProperty().divide(3.0));
        member2.prefWidthProperty().bind(widthProperty().divide(3.0));
        score.prefWidthProperty().bind(widthProperty().divide(6.0));

        players.getColumns().addAll(member1, member2);
        getColumns().addAll(name, players, score);
    }

    /**
     * @return The single instance of the Scoreboard (a JavaFX TableView)
     */
    public static Scoreboard getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Scoreboard();
            resizeFont(24);
        }
        return INSTANCE;
    }

    /**
     * Decrements the Scoreboard font based on the {@link #fontsizes} array.
     */
    public static void decrementFont(ActionEvent actionEvent) {
        Scoreboard instance = getInstance();
        --instance.pointer;
        try {
            resizeFont(instance.fontsizes[instance.pointer]);
            instance.refresh();
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logging.warning("Cannot set the desired size.");
            ++instance.pointer;
        }
    }

    /**
     * Increments the Scoreboard font based on the {@link #fontsizes} array.
     */
    public static void incrementFont(ActionEvent actionEvent) {
        Scoreboard instance = getInstance();
        ++instance.pointer;
        try {
            resizeFont(instance.fontsizes[instance.pointer]);
            instance.refresh();
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logging.warning("Cannot set the desired size.");
            --instance.pointer;
        }
    }

    private static void resizeFont(int size) {
        Scoreboard instance = getInstance();
        if (size > 0 && size < 100) {
            String style = ("-fx-font: " + size + "px Arial; -fx-alignment: CENTER");
            instance.name.setStyle(style);
            instance.member1.setStyle(style);
            instance.member2.setStyle(style);
            instance.score.setStyle(style);
            instance.players.setStyle(style);
            Logging.info("Font size set to " + size);
        } else {
            Logging.warning("Invalid font size!");
        }

    }

    /**
     * Refreshes the single instance of the Scoreboard.
     */
    public static void refreshScoreboard() {
        Scoreboard.getInstance().refresh();
    }
}
