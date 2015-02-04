package com.abcgeometrie.metier;

/**
 * Created by lucas on 21/01/15.
 */
public class Gagnant implements java.io.Serializable{

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

    public String read(Gagnant[] lstGagnants, String lang){
        String read = "";
        if(lstGagnants.length >= 1) {
            if (!(lstGagnants[0].equals(null))) {
                read = read + lstGagnants[0].stringByLang(lstGagnants[0],lang, "1");
            }
        }
        if(lstGagnants.length >= 2) {
            if (!(lstGagnants[1].equals(null))) {
                read = read + lstGagnants[1].stringByLang(lstGagnants[1],lang, "2");
            }
        }
        if(lstGagnants.length >= 3) {
            if (!(lstGagnants[2].equals(null))) {
                read = read + lstGagnants[2].stringByLang(lstGagnants[2],lang, "3");
            }
        }
        return read;
    }

    @Override
    public String toString() {
        return this.pseudo + " - " + this.score + " PTS";
    }

    public String stringByLang(Gagnant gagnant, String lang, String classement){
        if(lang.equals("fr")){
            if(classement.equals("1")) return " ; Premier : " + gagnant.pseudo + " - " + gagnant.score + " Points";
            if(classement.equals("2")) return " ; Deuxième : " + gagnant.pseudo + " - " + gagnant.score + " Points";
            if(classement.equals("3")) return " ; Troisième : " + gagnant.pseudo + " - " + gagnant.score + " Points";
        }
        if(lang.equals("es")){
            if(classement.equals("1")) return " ; Primero : " + gagnant.pseudo + " - " + gagnant.score + " Puntos";
            if(classement.equals("2")) return " ; Segundo : " + gagnant.pseudo + " - " + gagnant.score + " Puntos";
            if(classement.equals("3")) return " ; Tercera : " + gagnant.pseudo + " - " + gagnant.score + " Puntos";
        }
        if(lang.equals("en")){
            if(classement.equals("1")) return " ; First : " + gagnant.pseudo + " - " + gagnant.score + " Points";
            if(classement.equals("2")) return " ; Second : " + gagnant.pseudo + " - " + gagnant.score + " Points";
            if(classement.equals("3")) return " ; Third : " + gagnant.pseudo + " - " + gagnant.score + " Points";
        }
        return "";
    }
}
