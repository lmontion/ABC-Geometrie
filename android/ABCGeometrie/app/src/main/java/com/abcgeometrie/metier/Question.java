package com.abcgeometrie.metier;

import java.util.ArrayList;

/**
 * Created by lucas on 21/01/15.
 */
public class Question {
    private int id;
    private String libelleFR, libelleAN, libelleES, urlImgSol, urlImg1, urlImg2, urlImg3, theme;
    private ArrayList<Contrat> lstContrat;

    public Question(int id, String libelleFR, String libelleAN, String libelleES, String urlImgSol, String urlImg1, String urlImg2, String urlImg3, String theme, ArrayList<Contrat> lstContrat) {
        this.id = id;
        this.libelleFR = libelleFR;
        this.libelleAN = libelleAN;
        this.libelleES = libelleES;
        this.urlImgSol = urlImgSol;
        this.urlImg1 = urlImg1;
        this.urlImg2 = urlImg2;
        this.urlImg3 = urlImg3;
        this.theme = theme;
        this.lstContrat = lstContrat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelleFR() {
        return libelleFR;
    }

    public void setLibelleFR(String libelleFR) {
        this.libelleFR = libelleFR;
    }

    public String getLibelleAN() {
        return libelleAN;
    }

    public void setLibelleAN(String libelleAN) {
        this.libelleAN = libelleAN;
    }

    public String getLibelleES() {
        return libelleES;
    }

    public void setLibelleES(String libelleES) {
        this.libelleES = libelleES;
    }

    public String getUrlImgSol() {
        return urlImgSol;
    }

    public void setUrlImgSol(String urlImgSol) {
        this.urlImgSol = urlImgSol;
    }

    public String getUrlImg1() {
        return urlImg1;
    }

    public void setUrlImg1(String urlImg1) {
        this.urlImg1 = urlImg1;
    }

    public String getUrlImg2() {
        return urlImg2;
    }

    public void setUrlImg2(String urlImg2) {
        this.urlImg2 = urlImg2;
    }

    public String getUrlImg3() {
        return urlImg3;
    }

    public void setUrlImg3(String urlImg3) {
        this.urlImg3 = urlImg3;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public ArrayList<Contrat> getLstContrat() {
        return lstContrat;
    }

    public void setLstContrat(ArrayList<Contrat> lstContrat) {
        this.lstContrat = lstContrat;
    }
}
