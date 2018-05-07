package ctfchallenge;


import java.util.Collection;


public class Squadra {

    private String membro1;
    private String membro2;
    private String nomesquadra;
    private int punteggio;

    private final int id;
    private static int numerosquadre = 0;

    /**
     *
     * @param membro1
     * @param membro2
     * @param nomesquadra
     */
    public Squadra(String membro1, String membro2, String nomesquadra) {
        this.id = ++numerosquadre;
        this.nomesquadra = nomesquadra;
        this.membro1 = membro1;
        this.membro2 = membro2;
        this.punteggio = 0;
    }

    /**
     * Metodo che ritorna l'ID di una Squadra dato il suo nome
     *
     * @param nome Il nome della String
     * @param collection La collection da cui cercare.
     * @return
     */
    public static int nameToId(String nome, Collection<Squadra> collection) {
        int res = -1;
        for (Squadra temp : collection) {
            if (temp.getNomesquadra().equals(nome)) {
                res = temp.getId();
                break;
            }
        }
        return res;
    }

    /**
     *
     * @param o
     * @return
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
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + membro1.hashCode();
        hash = 41 * hash + membro2.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return membro1 + ", " + membro2;
    }
    public String getMembro1() {
        return membro1;
    }

    public String getMembro2() {
        return membro2;
    }

    public int getId() {
        return id;
    }

    public static int getNumerosquadre() {
        return numerosquadre;
    }

    public String getNomesquadra() {
        return nomesquadra;
    }

    public int getPunteggio() {
        return punteggio;
    }
    
    public void incrementPunteggio(int increment) {
        this.punteggio += increment;
    }

    public void setMembro1(String membro1) {
        this.membro1 = membro1;
    }

    public void setMembro2(String membro2) {
        this.membro2 = membro2;
    }

    public void setNomesquadra(String nomesquadra) {
        this.nomesquadra = nomesquadra;
    }
    
    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

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
    
}
