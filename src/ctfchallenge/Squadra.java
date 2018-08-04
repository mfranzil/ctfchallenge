package ctfchallenge;

import java.util.Collection;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */

// TODO Fix import of teams overriding counter
public class Squadra implements Comparable<Squadra> {

    private static int numerosquadre = 0;
    private final int id;
    private String membro1;
    private String membro2;
    private String nomesquadra;
    private int punteggio;

    /**
     * Costruttore standard che incrementa il conteggio del numero delle squadre e setta il punteggio a 0.
     *
     * @param nomesquadra Il nome della squadra
     * @param membro1     Il primo (o unico) membro della squadra
     * @param membro2     Il secondo membro della squadra
     */
    public Squadra(String nomesquadra, String membro1, String membro2) {
        this.id = ++numerosquadre;
        this.nomesquadra = nomesquadra;
        this.membro1 = membro1;
        this.membro2 = membro2;
        this.punteggio = 0;
    }


    /**
     * Costruttore utilizzato dal backup manager per creare squadre avendo tutti i dati.
     *
     * @param id          Un codice univoco che rappresenti la squadra
     * @param nomesquadra Il nome della squadra
     * @param membro1     Il primo (o unico) membro della squadra
     * @param membro2     Il secondo membro della squadra
     * @param punteggio   Il punteggio da assegnare alla squadra
     */
    public Squadra(int id, String nomesquadra, String membro1, String membro2, int punteggio) {
        this.id = id;
        this.nomesquadra = nomesquadra;
        this.membro1 = membro1;
        this.membro2 = membro2;
        this.punteggio = punteggio;
    }


    public static int getNumerosquadre() {
        return numerosquadre;
    }

    /**
     * Metodo che prende in entrata una Collection di squadre e un nome e ritorna la squadra corrispondente al nome.
     *
     * @param name    Il nome della squadra da cercare
     * @param squadre La Collection in cui cercare
     * @return Una Squadra che per prima ha un match con il valore cercato.
     */
    public static Squadra getSquadraFromName(String name, Collection<Squadra> squadre) {
        Squadra res = null;
        for (Squadra i : squadre) {
            if (name.equals(i.getNomesquadra())) {
                res = i;
                break;
            }
        }
        return res;
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
        } else if (!(o instanceof Squadra)) { // else if (var.getClass() != this.getClass())
            res = false;
        } else {
            res = membro1.equals(((Squadra) o).getMembro1()) && membro2.equals(((Squadra) o).getMembro2());
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
        hash = 41 * hash + membro1.hashCode();
        hash = 41 * hash + membro2.hashCode();
        return hash;
    }

    /**
     * Metodo standard toString, implementato citando semplicemente i membri.
     *
     * @return Una String contenente la versiona in Stringa della persona.
     */
    @Override
    public String toString() {
        return membro1 + ", " + membro2;
    }

    public String getMembro1() {
        return membro1;
    }

    public void setMembro1(String membro1) {
        this.membro1 = membro1;
    }

    public String getMembro2() {
        return membro2;
    }

    public void setMembro2(String membro2) {
        this.membro2 = membro2;
    }

    public int getId() {
        return id;
    }

    public String getNomesquadra() {
        return nomesquadra;
    }

    public void setNomesquadra(String nomesquadra) {
        this.nomesquadra = nomesquadra;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    /**
     * Metodo che incrementa in maniera arbitraria il punteggio della squadra
     *
     * @param increment Un int (anche negativo) usato per modificare il punteggio.
     */
    public void incrementPunteggio(int increment) {
        this.punteggio += increment;
    }

    @Override
    public int compareTo(Squadra o) {
        return o.getPunteggio() - this.getPunteggio();
    }
}
