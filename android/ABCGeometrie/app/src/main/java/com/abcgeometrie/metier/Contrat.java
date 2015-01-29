package com.abcgeometrie.metier;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lucas on 21/01/15.
 */
public class Contrat implements java.io.Serializable{
    private int id, nbPoints;
    private String libelle, niveau, theme;
    private ArrayList<Question> lstQuestions, lstQuestionsPosees;

    public Contrat(int id, int nbPoints, String libelle, String niveau, String theme, ArrayList<Question> lstQuestions) {
        this.id = id;
        this.nbPoints = nbPoints;
        this.libelle = libelle;
        this.niveau = niveau;
        this.theme = theme;
        this.lstQuestions = lstQuestions;
        this.lstQuestionsPosees = new ArrayList<Question>();

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

    public Question chooseAQuestion(){
        Random r = new Random();
        int index = r.nextInt(lstQuestions.size());
        Question laQuestion = lstQuestions.get(index);
        //if(dejaPosee(laQuestion) == true){
            //chooseAQuestion();
        //}
        //lstQuestionsPosees.add(laQuestion);
        lstQuestions.remove(index);
        return laQuestion;
    }

    public boolean dejaPosee(Question question){
        boolean dejaPosee = false;
        if(lstQuestionsPosees.contains(question))
            dejaPosee = true;
        return dejaPosee;
    }

}
