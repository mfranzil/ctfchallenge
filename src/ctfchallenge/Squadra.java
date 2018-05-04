/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import java.util.Collection;
import java.util.Iterator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author matte
 */
public class Squadra {

    private final StringProperty membro1;
    private final StringProperty membro2;
    private final StringProperty nomesquadra;
    private       StringProperty punteggio;
       
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
        this.nomesquadra = new SimpleStringProperty(nomesquadra);
        this.membro1 = new SimpleStringProperty(membro1);
        this.membro2 = new SimpleStringProperty(membro2);
        this.punteggio = new SimpleStringProperty(Integer.toString(0));
    }
    
    
    /**
     * Metodo che ritorna l'ID di una Squadra dato il suo nome
     * @param nome Il nome della String
     * @param collection La collection da cui cercare.
     * @return
     */
    public static int nameToId(String nome, Collection<Squadra> collection) {
        int res = -1;
        Iterator<Squadra> iter = collection.iterator();
        while (iter.hasNext()) {
            Squadra temp = iter.next();
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
        if (o == null)
            res = false;
        else if (!(o instanceof Squadra)) // else if (var.getClass() != this.getClass())
            res = false;
        else
            res = membro1.equals(((Squadra) o).membro1) && membro2.equals(((Squadra) o).membro2);
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return membro1.get() + ", " + membro2.get();
    }

    /**
     *
     * @return
     */
    public String getMembro1() {
        return membro1.get();
    }

    /**
     *
     * @return
     */
    public String getMembro2() {
        return membro2.get();
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public static int getNumerosquadre() {
        return numerosquadre;
    }

    /**
     *
     * @return
     */
    public String getPunteggio() {
        return punteggio.get();
    }

    /**
     *
     * @return
     */
    public String getNomesquadra() {
        return nomesquadra.get();
    }

    /**
     *
     * @param punteggio
     */
    public void setPunteggio(StringProperty punteggio) {
        this.punteggio = punteggio;
    }
    
    
    
}
