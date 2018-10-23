package ctfchallenge;

import java.io.Serializable;

/**
 * @author Matteo Franzil
 * @version 1.2
 */

public class Team implements Comparable<Team>, Serializable {

    private String player1;
    private String player2;
    private String name;
    private int score;


    /**
     * Standard constructor.
     *
     * @param name    The name of the team.
     * @param player1 The first (or only) team member.
     * @param player2 The second team member.
     * @param score   The score to assign.
     */
    public Team(String name, String player1, String player2, int score) {
        this.name = name;
        this.player1 = player1;
        this.player2 = player2;
        this.score = score;
    }

    /**
     * Method that compares two teams, based on the name of its members.
     *
     * @param o The item to compare.
     * @return A boolean representing the result.
     */
    @Override
    public boolean equals(Object o) {
        boolean res;
        if (o == null) {
            res = false;
        } else if (!(o instanceof Team)) { // else if (var.getClass() != this.getClass())
            res = false;
        } else {
            res = player1.equals(((Team) o).getPlayer1()) && player2.equals(((Team) o).getPlayer2());
        }
        return res;
    }

    /**
     * Standard HashCode generation.
     *
     * @return An Integer representing the HashCode.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + player1.hashCode();
        hash = 41 * hash + player2.hashCode();
        return hash;
    }

    @Override
    public int compareTo(Team o) {
        return o.getScore() - this.getScore();
    }

    /**
     * Standard toString method.
     *
     * @return A String containing name, members and score of the Team.
     */
    @Override
    public String toString() {
        return name + ": " + player1 + ", " + player2 + " - " + score + " points";
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Method to freely increment or decrement the score.
     *
     * @param increment A signed integer to add or subtract the score.
     */
    public void incrementScore(int increment) {
        this.score += increment;
    }
}