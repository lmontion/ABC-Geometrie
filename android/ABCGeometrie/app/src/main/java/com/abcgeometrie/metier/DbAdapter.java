package com.abcgeometrie.metier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DbAdapter {

    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String CREATE_TABLE_CONTRAT =
            "create table contrat (_id integer primary key autoincrement, libelle text not null, nbPoints integer, niveau integer, theme text);";
    private static final String CREATE_TABLE_GAGNANT =
            "create table gagnant (_id integer primary key autoincrement, pseudo text not null, score integer, idContrat integer);";
    private static final String CREATE_TABLE_QUESTION =
            "create table question (_id integer primary key autoincrement, libelleFR text, libelleEN text, libelleES text," +
                    " urlImgSol text, urlImg1 text, urlImg2 text, urlImg3 text);";
    private static final String CREATE_TABLE_APPARTENIR =
            "create table appartenir (idContrat integer, idQuestion integer, primary key(idContrat,idQuestion));";

    private static final String INSERT_TABLE_APPARTENIR =
            "insert into appartenir (idContrat, idQuestion) values" +
                    "(1,1)," + "(2,1)," + "(3,1)," + "(4,1)," + "(5,1)," + "(6,1)," + "(7,1)," + "(8,1)," +
                    "(9,1)," + "(1,2)," + "(2,2)," + "(3,2)," + "(4,2)," + "(5,2)," + "(6,2)," + "(7,2)," +
                    "(8,2)," + "(9,2)," + "(10,2)," + "(11,2)," + "(13,2)," + "(14,2)," + "(15,2)," + "(10,1)," +
                    "(11,1)," + "(12,1)," + "(13,1)," + "(14,1)," + "(15,1)," + "(1,3)," + "(2,3)," + "(4,3)," +
                    "(5,3)," + "(6,3)," + "(7,3)," + "(8,3)," + "(9,3);";

    private static final String INSERT_TABLE_QUESTION =
            "insert into question (libelleFR, libelleEN, libelleES, urlImgSol, urlImg1, urlImg2, urlImg3) values" +
                    "('Trouves le carré blanc', 'Find the white square', 'je sais pas dire', '/bonneImg.png', '/mauvaiseImg1.png', '/mauvaiseImg2.png', '/mauvaiseImg3.png')," +
                    "('Trouves le carré bleu', 'Find the blue square', 'je sais pas dire', '/bonneImg.png', '/mauvaiseImg1.png', '/mauvaiseImg2.png', '/mauvaiseImg3.png'), " +
                    "('Trouves le carré vert', 'Find the green square', 'je sais pas dire', '/bonneImg.png', '/mauvaiseImg1.png', '/mauvaiseImg2.png', '/mauvaiseImg3.png'); ";

    private static final String INSERT_TABLE_CONTRAT =
            "insert into contrat (libelle, nbPoints, niveau, theme) values" +
                    "('Contrat 10 points', 10, 1, 'couleurs')," +
                    "('Contrat 20 points', 20, 1, 'couleurs')," +
                    "('Contrat 40 points', 40, 1, 'couleurs')," +
                    "('Contrat 10 points', 10, 1, 'formes')," +
                    "('Contrat 20 points', 20, 1, 'formes')," +
                    "('Contrat 40 points', 40, 1, 'formes')," +
                    "('Contrat 10 points', 10, 1, 'couleurs et formes')," +
                    "('Contrat 20 points', 20, 1, 'couleurs et formes')," +
                    "('Contrat 40 points', 40, 1, 'couleurs et formes')," +
                    "('Contrat 10 points', 10, 2, '')," +
                    "('Contrat 20 points', 20, 2, '')," +
                    "('Contrat 40 points', 40, 2, '')," +
                    "('Contrat 10 points', 10, 3, '')," +
                    "('Contrat 20 points', 20, 3, '')," +
                    "('Contrat 40 points', 40, 3, '');";

    private static final String INSERT_TABLE_GAGNANT =
            "insert into gagnant (pseudo, score, idContrat) values" +
                    "('Bob', 450, 1)," + "('Willy', 250, 1)," + "('Steeve', 350, 1)," + "('Tom', 500, 1)," +
                    "('Louis', 100, 1)," + "('Edmond', 150, 2)," + "('Lucas', 50, 2)," + "('Cathy', 450, 2)," +
                    "('Enzo', 600, 2)," + "('Vivient', 250, 2)," + "('Kathleen', 100, 2)," + "('Léa', 450, 3)," +
                    "('Andréa', 500, 3)," + "('Lucienne', 100, 3)," + "('Evian', 50, 3)," + "('Patrick', 800, 3)," +
                    "('Jimmy', 400, 4)," + "('Léandra', 450, 6)," + "('Stephane', 200, 6)," + "('Alexia', 250, 6)," +
                    "('Franck', 200, 6)," + "('Francis', 100, 6)," + "('Xavier', 150, 6)," + "('Gilbert', 200, 6)," +
                    "('Berta', 250, 7)," + "('Boby', 450, 7)," + "('Bob', 450, 7)," + "('Brian', 450, 7)," +
                    "('Gustave', 250, 8)," + "('Fred', 450, 8)," + "('Fanny', 600, 8)," + "('Mickeal', 200, 9)," +
                    "('Lucie', 200, 10)," + "('Lydie', 250, 10)," + "('Linda', 300, 10)," + "('Julie', 450, 11)," +
                    "('Thomas', 200, 11)," + "('Timéo', 150, 12)," + "('Manon', 200, 12)," + "('Tristan', 200, 12)," +
                    "('Yanick', 900, 13)," + "('Luc', 50, 13)," + "('Valérie', 150, 14)," + "('Sylvain', 450, 15)," +
                    "('Ludivine', 450, 15)," + "('Solène', 200, 15)," + "('Morgane', 300, 15)," + "('Mylène', 550, 15)," +
                    "('Namie', 300, 15)," + "('Maxime', 200, 15)," + "('Julien', 250, 15)," + "('Jule', 300, 15)," +
                    "('Hakim', 200, 15)," + "('Carlos', 50, 15)," + "('Adrien', 600, 15)," + "('Sophie', 150, 15)," +
                    "('Serge', 450, 15)," + "('Pascale', 150, 15)," + "('Mathieu', 650, 15)," + "('Valentin', 550, 15)," +
                    "('Hubert', 300, 15)," + "('Seb', 400, 15)," + "('Mohammed', 200, 15)," + "('Aziz', 700, 15);";


    private static final String DATABASE_NAME = "ABC_Geometrie";
    private static final int DATABASE_VERSION = 6;
    private final Context mCtx;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CONTRAT);
            db.execSQL(CREATE_TABLE_GAGNANT);
            db.execSQL(CREATE_TABLE_QUESTION);
            db.execSQL(CREATE_TABLE_APPARTENIR);
            db.execSQL(INSERT_TABLE_CONTRAT);
            db.execSQL(INSERT_TABLE_GAGNANT);
            db.execSQL(INSERT_TABLE_QUESTION);
            db.execSQL(INSERT_TABLE_APPARTENIR);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contrat");
            db.execSQL("DROP TABLE IF EXISTS gagnant");
            db.execSQL("DROP TABLE IF EXISTS question");
            db.execSQL("DROP TABLE IF EXISTS appartenir");
            onCreate(db);
        }
    }

    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public ArrayList<String> getContrat(){
        Cursor contrat = mDb.query("contrat", new String[]{"libelle"}, null, null, null, null, null);
        ArrayList<String> contrat2 = new ArrayList<String>();
        while(contrat.moveToNext()){
            contrat2.add(contrat.getString(0));
        }
        return contrat2;
    }

    public ArrayList<String> getGagnant(){
        Cursor c = mDb.query("gagnant", new String[]{"pseudo"}, null, null, null, null, null);
        ArrayList<String> gagnants = new ArrayList<String>();
        while(c.moveToNext()){
            gagnants.add(c.getString(0));
        }
        return gagnants;
    }

    public ArrayList<String> getQuestion(){
        Cursor c = mDb.query("question", new String[]{"libelleFR"}, null, null, null, null, null);
        ArrayList<String> questions = new ArrayList<String>();
        while(c.moveToNext()){
            questions.add(c.getString(0));
        }
        return questions;
    }

    public ArrayList<String> getAppartenir(){
        Cursor c = mDb.query("appartenir", new String[]{"idContrat", "idQuestion"}, null, null, null, null, null);
        ArrayList<String> appartenir = new ArrayList<String>();
        while(c.moveToNext()){
            appartenir.add(c.getString(0));
        }
        return appartenir;
    }

    public ArrayList<String> getContratsByNiveau(int niveau){
        Cursor c = mDb.query("contrat", new String[]{"_id", "libelle"}, "niveau="+niveau, null, null, null, null);
        ArrayList<String> contrats = new ArrayList<String>();
        while(c.moveToNext()){
            contrats.add(c.getString(0));
            contrats.add(c.getString(1));
        }
        return contrats;
    }

    public ArrayList<String> getThemeByNiveau(int niveau){
        Cursor c = mDb.query(true,"contrat", new String[]{"theme"}, "niveau="+niveau, null, null, null, null, null);
        ArrayList<String> themes = new ArrayList<String>();
        while(c.moveToNext()){
            themes.add(c.getString(0));
        }
        return themes;
    }

    public ArrayList<String> getcontratsByNiveauAndTheme(int niveau, String theme){
        Cursor c = mDb.query("contrat", new String[]{"_id", "libelle"}, "(niveau="+niveau+" AND theme='"+theme+"')", null, null, null, null);
        ArrayList<String> contrats = new ArrayList<String>();
        while(c.moveToNext()){
            contrats.add(c.getString(0));
            contrats.add(c.getString(1));
        }
        return contrats;
    }

    public ArrayList<String> getQuestionByContrat(int idContrat){
        Cursor c = mDb.query("contrat as c, appartenir as a, question", new String[]{"c._id", "question._id", "question.libelleFR"}, "(a.idQuestion=question._id AND c._id="+idContrat+" AND c._id = a.idContrat)", null, null, null, null);
        ArrayList<String> contrats = new ArrayList<String>();
        while(c.moveToNext()){
            contrats.add(c.getString(0));
            contrats.add(c.getString(1));
            contrats.add(c.getString(2));
        }
        return contrats;
    }

    public ArrayList<String> getNbPointByContrat(int idContrat){
        Cursor c = mDb.query("contrat", new String[]{"nbPoints"}, "_id="+idContrat, null, null, null, null);
        ArrayList<String> nbPoints = new ArrayList<String>();
        while(c.moveToNext()){
            nbPoints.add(c.getString(0));
        }
        return nbPoints;
    }

    public ArrayList<String> getScoreByContrat(int idContrat){
        Cursor c = mDb.query("gagnant", new String[]{"pseudo", "score"}, "idContrat="+idContrat, null, null, null, "score desc", "10");
        ArrayList<String> scores = new ArrayList<String>();
        while(c.moveToNext()){
            scores.add(c.getString(0));
            scores.add(c.getString(1));
        }
        return scores;
    }

    public void insertScore(String pseudo, int score, int idContrat){
        ContentValues val = new ContentValues();
        val.put("pseudo", pseudo);
        val.put("score", score);
        val.put("idContrat", idContrat);
        mDb.insert("gagnant", null, val);
    }
}

