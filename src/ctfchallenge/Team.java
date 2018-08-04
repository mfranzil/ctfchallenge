package ctfchallenge;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */

public class Team implements Comparable<Team> {

    private String member1;
    private String member2;
    private String teamName;
    private int score;

    /**
     * Costruttore standard che incrementa il conteggio del numero delle squadre e setta il score a 0.
     *
     * @param teamName Il nome della squadra
     * @param member1  Il primo (o unico) membro della squadra
     * @param member2  Il secondo membro della squadra
     */
    public Team(String teamName, String member1, String member2) {
        this.teamName = teamName;
        this.member1 = member1;
        this.member2 = member2;
        this.score = 0;
    }


    /**
     * Costruttore utilizzato dal backup manager per creare squadre avendo tutti i dati.
     *
     * @param teamName Il nome della squadra
     * @param member1  Il primo (o unico) membro della squadra
     * @param member2  Il secondo membro della squadra
     * @param score    Il score da assegnare alla squadra
     */
    public Team(String teamName, String member1, String member2, int score) {
        this.teamName = teamName;
        this.member1 = member1;
        this.member2 = member2;
        this.score = score;
    }

    /**
     * Metodo standard che fa il paragone tra due Squadre.
     *
     * @param o Un oggetto da comparare.
     * @return Un Boolean che rappresenta il risultato della comparazione.
     */
    @Override
    public boolean equals(Object o) {
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
     * Metodo standard di generazione dell'hashcode (generato da Netbeans)
     *
     * @return Un int che rappresenta l'hash della Persona.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + member1.hashCode();
        hash = 41 * hash + member2.hashCode();
        return hash;
    }

    @Override
    public int compareTo(Team o) {
        return o.getScore() - this.getScore();
    }

    /**
     * Metodo standard toString, implementato citando semplicemente i membri.
     *
     * @return Una String contenente la versiona in Stringa della persona.
     */
    @Override
    public String toString() {
        return member1 + ", " + member2;
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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Metodo che incrementa in maniera arbitraria il score della squadra
     *
     * @param increment Un int (anche negativo) usato per modificare il score.
     */
    public void incrementScore(int increment) {
        this.score += increment;
    }
}