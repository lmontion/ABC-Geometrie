package com.abcgeometrie.metier;

import java.util.ArrayList;

/**
 * Created by lucas on 21/01/15.
 */
public class Contrat {
    private int id, nbPoints;
    private String libelle, niveau, theme;
    private ArrayList<Question> lstQuestions;

    public Contrat(int id, int nbPoints, String libelle, String niveau, String theme, ArrayList<Question> lstQuestions) {
        this.id = id;
        this.nbPoints = nbPoints;
        this.libelle = libelle;
        this.niveau = niveau;
        this.theme = theme;
        this.lstQuestions = lstQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public ArrayList<Question> getLstQuestions() {
        return lstQuestions;
    }

    public void setLstQuestions(ArrayList<Question> lstQuestions) {
        this.lstQuestions = lstQuestions;
    }
}
