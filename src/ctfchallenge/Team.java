package ctfchallenge;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Matteo Franzil
 * @version 1.2
 */

public class Team implements Comparable<Team> {

    private String member1;
    private String member2;
    private String name;
    private int score;


    /**
     * Standard constructor.
     *
     * @param name    The name of the team.
     * @param member1 The first (or only) team member.
     * @param member2 The second team member.
     * @param score   The score to assign.
     */
    public Team(String name, String member1, String member2, int score) {
        this.name = name;
        this.member1 = member1;
        this.member2 = member2;
        this.score = score;
    }

    /**
     * Method that compares two teams, based on the name of its members.
     *
     * @param o The item to compare.
     * @return A boolean representing the result.
     */
    @Override
    public boolean equals(@Nullable Object o) {
        boolean res;
        if (o == null) {
            res = false;
        } else if (!(o instanceof Team)) { // else if (var.getClass() != this.getClass())
            res = false;
        } else {
            res = member1.equals(((Team) o).getMember1()) && member2.equals(((Team) o).getMember2());
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
        hash = 41 * hash + member1.hashCode();
        hash = 41 * hash + member2.hashCode();
        return hash;
    }

    @Override
    public int compareTo(@NotNull Team o) {
        return o.getScore() - this.getScore();
    }

    /**
     * Standard toString method.
     *
     * @return A String containing name, members and score of the Team.
     */
    @NotNull
    @Override
    public String toString() {
        return name + ": " + member1 + ", " + member2 + " - " + score + " points";
    }

    public String getMember1() {
        return member1;
    }

    public void setMember1(String member1) {
        this.member1 = member1;
    }

    public String getMember2() {
        return member2;
    }

    public void setMember2(String member2) {
        this.member2 = member2;
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