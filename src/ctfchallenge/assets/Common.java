package ctfchallenge.assets;

/**
 * @author Matteo Franzil
 * @version 20181108v1
 */
public class Common {
    /**
     * The total number of rounds.
     */
    public static final int MAX_ROUNDS = 4;

    /**
     * The number of teams participating. This field should be left as it is and modified by the program itself.
     */
    public static int teamNumber;

    /**
     * The maximum number of teams allowed to get a variable bonus.
     */
    public static int MAX_TEAMS_BONUS = 5;

    /**
     * The value of the fixed bonus given to teams.
     */
    public static int FIXED_BONUS = 5;

    /**
     * This method return the score for each round. Must be modified accordingly with {@link #MAX_ROUNDS}.
     *
     * @param i The round index.
     * @return The score of the round.
     */
    public static int getScore(int i) {
        int res;
        switch (i) {
            case 1:
                res = 5;
                break;
            case 2:
                res = 10;
                break;
            case 3:
                res = 15;
                break;
            case 4:
                res = 25;
                break;
            default:
                res = -1;
                break;
        }
        return res;
    }

    /**
     * This method return the ordinal name of each round number. Must be modified accordingly with {@link #MAX_ROUNDS}.
     *
     * @param i The round index.
     * @return A String, representing the round in English ordinal naming convention.
     */
    @Deprecated
    public static String intToText(int i) {
        String res;
        switch (i) {
            case 1:
                res = "First";
                break;
            case 2:
                res = "Second";
                break;
            case 3:
                res = "Third";
                break;
            case 4:
                res = "Fourth";
                break;
            default:
                res = null;
                break;
        }
        return res;
    }
}
