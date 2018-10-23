package ctfchallenge.assets;

public class Common {
    /**
     * The number of teams allowed to get a bonus for finishing in the top positions.
     */
    public static final int MAX_TEAMS_BONUS = 5;
    /**
     * The total number of rounds.
     */
    public static final int MAX_ROUNDS = 5;
    /**
     * The current round.
     */
    public static int currentRound = 0;
    /**
     * The number of teams participating. This field should be left as it is and modified by the program itself.
     */
    public static int teamNumber;

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
            case 5:
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
            case 5:
                res = "Fifth";
                break;
            default:
                res = null;
                break;
        }
        return res;
    }
}
