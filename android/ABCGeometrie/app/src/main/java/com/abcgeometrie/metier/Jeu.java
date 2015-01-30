package com.abcgeometrie.metier;

/**
 * Created by lucas on 28/01/15.
 */
public class Jeu implements java.io.Serializable{

    private int nbQuestionsNecessaires;
    private int nbQuestionsReussis;
    private static long tempsDebut;
    private long tempsPasse;

    public Jeu(int nbQuestionsNecessaires, int nbQuestionsReussis) {
        this.nbQuestionsNecessaires = nbQuestionsNecessaires;
        this.nbQuestionsReussis = nbQuestionsReussis;
        this.tempsDebut = java.lang.System.currentTimeMillis() ;
    }

    // Arret du chrono
    public void stopChrono() {
        long tempsFin = java.lang.System.currentTimeMillis() ;
        long tempsPasse = tempsFin - tempsDebut ;
        tempsPasse = tempsPasse/1000;
        setTempsPasse(tempsPasse);
    }

    public long getTempsPasse() {
        return tempsPasse;
    }

    public void setTempsPasse(long tempsPasse) {
        this.tempsPasse = tempsPasse;
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

    public int calculScore(){
        int nbMauvaiseRep = this.nbQuestionsNecessaires - this.nbQuestionsReussis;
        int score = 50*this.nbQuestionsReussis - 25*nbMauvaiseRep;
        float newTempsPasse = ((float)this.tempsPasse)/60;
        score = (int)(score / newTempsPasse);
        if(score < 100) {
            score = 100;
        }
        return score;
    }
}

