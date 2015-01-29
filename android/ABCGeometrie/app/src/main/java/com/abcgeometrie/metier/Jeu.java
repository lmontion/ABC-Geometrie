package com.abcgeometrie.metier;

/**
 * Created by lucas on 28/01/15.
 */
public class Jeu implements java.io.Serializable{

    private int tempsSec;
    private int nbQuestionsNecessaires;
    private int nbQuestionsReussis;

    public Jeu(int tempsSec, int nbQuestionsNecessaires, int nbQuestionsReussis) {
        this.tempsSec = tempsSec;
        this.nbQuestionsNecessaires = nbQuestionsNecessaires;
        this.nbQuestionsReussis = nbQuestionsReussis;
    }

    public int getTemps() {
        return tempsSec;
    }

    public void setTemps(int temps) {
        this.tempsSec = temps;
    }

    public int getNbQuestionsNecessaires() {
        return nbQuestionsNecessaires;
    }

    public void setNbQuestionsNecessaires(int nbQuestionsNecessaires) {
        this.nbQuestionsNecessaires = nbQuestionsNecessaires;
    }

    public int getNbQuestionsReussis() {
        return nbQuestionsReussis;
    }

    public void setNbQuestionsReussis(int nbQuestionsReussis) {
        this.nbQuestionsReussis = nbQuestionsReussis;
    }

    private int calculScore(){
        int nbMauvaiseRep = nbQuestionsNecessaires - nbQuestionsReussis;
        int score = 50*nbQuestionsReussis - 25*nbMauvaiseRep;
        if(score < 100) {
            score = 100;
        }
        return score;
    }
}

