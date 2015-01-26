package com.abcgeometrie.metier;

/**
 * Created by lucas on 21/01/15.
 */
public class Gagnant {

    private int id, score;
    private String pseudo;
    private Contrat contrat;

    public Gagnant(int id, int score, String pseudo, Contrat contrat) {
        this.id = id;
        this.score = score;
        this.pseudo = pseudo;
        this.contrat = contrat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }
}
