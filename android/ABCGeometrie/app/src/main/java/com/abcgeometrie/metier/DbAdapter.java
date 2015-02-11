package com.abcgeometrie.metier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.abcgeometrie.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DbAdapter {

    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String CREATE_TABLE_CONTRAT =
            "create table contrat (_id integer primary key, libelle text not null, nbPoints integer, niveau integer, theme text);";
    private static final String CREATE_TABLE_GAGNANT =
            "create table gagnant (_id integer primary key autoincrement, pseudo text not null, score integer, idContrat integer);";
    private static final String CREATE_TABLE_QUESTION =
            "create table question (_id integer primary key, libelleFR text, libelleEN text, libelleES text," +
                    " urlImgSol text, urlImg1 text, urlImg2 text, urlImg3 text);";
    private static final String CREATE_TABLE_APPARTENIR =
            "create table appartenir (idContrat integer, idQuestion integer, primary key(idContrat,idQuestion));";

    /*private static final String INSERT_TABLE_GAGNANT =
            "insert into gagnant (pseudo, score, idContrat) values" +
                    "('Bob', 450, 0)," + "('Willy', 250, 1)," + "('Steeve', 350, 1)," + "('Tom', 500, 1)," +
                    "('Louis', 100, 1)," + "('Edmond', 150, 2)," + "('Lucas', 50, 2)," + "('Cathy', 450, 2)," +
                    "('Enzo', 600, 2)," + "('Vivient', 250, 2)," + "('Kathleen', 100, 2)," + "('Léa', 450, 3)," +
                    "('Andréa', 500, 3)," + "('Lucienne', 100, 3)," + "('Evian', 50, 3)," + "('Patrick', 800, 3)," +
                    "('Jimmy', 400, 4)," + "('Léandra', 450, 6)," + "('Stephane', 200, 6)," + "('Alexia', 250, 6)," +
                    "('Franck', 200, 6)," + "('Francis', 100, 6)," + "('Xavier', 150, 6)," + "('Gilbert', 200, 6)," +
                    "('Berta', 250, 7)," + "('Boby', 450, 7)," + "('Bob', 450, 7)," + "('Brian', 450, 7)," +
                    "('Gustave', 250, 8)," + "('Fred', 450, 8)," + "('Fanny', 600, 8)," + "('Mickeal', 200, 9)," +
                    "('Lucie', 200, 10)," + "('Lydie', 250, 10)," + "('Linda', 300, 10)," + "('Julie', 450, 11)," +
                    "('Thomas', 200, 11)," + "('Timéo', 150, 12)," + "('Manon', 200, 12)," + "('Tristan', 200, 12)," +
                    "('Yanick', 900, 13)," + "('Luc', 50, 13)," + "('Valérie', 150, 14)," + "('Sylvain', 4500, 0)," +
                    "('Ludivine', 4500, 0)," + "('Solène', 20000, 0)," + "('Morgane', 3000, 0)," + "('Mylène', 5500, 0)," +
                    "('Namie', 3000, 0)," + "('Maxime', 2000, 0)," + "('Julien', 25000, 0)," + "('Jule', 30000, 0)," +
                    "('Hakim', 20000, 0)," + "('Carlos', 5000, 0)," + "('Adrien', 6000, 0)," + "('Sophie', 150, 15)," +
                    "('Serge', 4500, 0)," + "('Pascale', 15000, 0)," + "('Mathieu', 65000, 0)," + "('Valentin', 5500, 0)," +
                    "('Hubert', 30000, 0)," + "('Seb', 4000, 0)," + "('Mohammed', 20000, 0)," + "('Lucas le champion !!', 100000, 0);";*/

    private static final String INSERT_TABLE_CONTRAT =
            "insert into contrat values" +
                    "(0,'Contrat 10 points', 10, 1, 'couleurs')," +
                    "(1,'Contrat 20 points', 20, 1, 'couleurs')," +
                    "(2,'Contrat 40 points', 40, 1, 'couleurs')," +
                    "(3,'Contrat 10 points', 10, 1, 'formes')," +
                    "(4,'Contrat 20 points', 20, 1, 'formes')," +
                    "(5,'Contrat 40 points', 40, 1, 'formes')," +
                    "(6,'Contrat 10 points', 10, 1, 'couleurs et formes')," +
                    "(7,'Contrat 20 points', 20, 1, 'couleurs et formes')," +
                    "(8,'Contrat 40 points', 40, 1, 'couleurs et formes')," +
                    "(9,'Contrat 10 points', 10, 2, '')," +
                    "(10,'Contrat 20 points', 20, 2, '')," +
                    "(11,'Contrat 40 points', 40, 2, '')," +
                    "(12,'Contrat 10 points', 10, 3, '')," +
                    "(13,'Contrat 20 points', 20, 3, '')," +
                    "(14,'Contrat 40 points', 40, 3, '');";

    private static String INSERT_TABLE_QUESTION_LVL1;
    private static String INSERT_TABLE_APPARTENIR_LVL1;
    private static String INSERT_TABLE_QUESTION_LVL2;
    private static String INSERT_TABLE_APPARTENIR_LVL2;
    private static String INSERT_TABLE_QUESTION_LVL3;
    private static String INSERT_TABLE_APPARTENIR_LVL3;

    private static final String DATABASE_NAME = "ABC_Geometrie";
    private static final int DATABASE_VERSION = 55;
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
            //db.execSQL(INSERT_TABLE_GAGNANT);
            //db.execSQL(INSERT_TABLE_QUESTION);
            //db.execSQL(INSERT_TABLE_APPARTENIR);
            db.execSQL(INSERT_TABLE_QUESTION_LVL1);
            db.execSQL(INSERT_TABLE_APPARTENIR_LVL1);
            db.execSQL(INSERT_TABLE_QUESTION_LVL2);
            db.execSQL(INSERT_TABLE_APPARTENIR_LVL2);
            db.execSQL(INSERT_TABLE_QUESTION_LVL3);
            db.execSQL(INSERT_TABLE_APPARTENIR_LVL3);
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
        INSERT_TABLE_QUESTION_LVL1 = getValueQuestionNiv1();
        INSERT_TABLE_APPARTENIR_LVL1 = getValueAppartenirNiv1();
        INSERT_TABLE_QUESTION_LVL2 = getValueQuestionNiv2();
        INSERT_TABLE_APPARTENIR_LVL2 = getValueAppartenirNiv2();
        INSERT_TABLE_QUESTION_LVL3 = getValueQuestionNiv3();
        INSERT_TABLE_APPARTENIR_LVL3 = getValueAppartenirNiv3();
    }

    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public String getValueQuestionNiv1(){
        String ligne;
        String temp = "";
        StringBuffer buf = new StringBuffer();
        InputStream is = mCtx.getResources().openRawResource(R.raw.dataq1);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            if (is!=null) {
                while ((ligne = reader.readLine()) != null) {
                    if (ligne.contains("@")){
                        ligne = ligne.replace("@","‘");
                    }
                    temp += ligne;
                }
            }
            is.close();
        } catch(IOException e) {
            Log.e("file", e.getMessage());
        }
        return temp;
    }

    public String getValueAppartenirNiv1(){
        String ligne;
        String temp = "";
        StringBuffer buf = new StringBuffer();
        InputStream is = mCtx.getResources().openRawResource(R.raw.dataapp1);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            if (is!=null) {
                while ((ligne = reader.readLine()) != null) {
                    temp += ligne;
                }
            }
            is.close();
        } catch(IOException e) {
            Log.e("file", e.getMessage());
        }
        return temp;
    }

    public String getValueQuestionNiv2(){
        String ligne;
        String temp = "";
        StringBuffer buf = new StringBuffer();
        InputStream is = mCtx.getResources().openRawResource(R.raw.dataq2);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            if (is!=null) {
                while ((ligne = reader.readLine()) != null) {
                    if (ligne.contains("@")){
                        ligne = ligne.replace("@","‘");
                    }
                    temp += ligne;
                }
            }
            is.close();
        } catch(IOException e) {
            Log.e("file", e.getMessage());
        }
        return temp;
    }

    public String getValueAppartenirNiv2(){
        String ligne;
        String temp = "";
        StringBuffer buf = new StringBuffer();
        InputStream is = mCtx.getResources().openRawResource(R.raw.dataapp2);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            if (is!=null) {
                while ((ligne = reader.readLine()) != null) {
                    temp += ligne;
                }
            }
            is.close();
        } catch(IOException e) {
            Log.e("file", e.getMessage());
        }
        return temp;
    }

    public String getValueQuestionNiv3(){
        String ligne;
        String temp = "";
        StringBuffer buf = new StringBuffer();
        InputStream is = mCtx.getResources().openRawResource(R.raw.dataq3);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            if (is!=null) {
                while ((ligne = reader.readLine()) != null) {
                    if (ligne.contains("@")){
                        ligne = ligne.replace("@","‘");
                    }
                    temp += ligne;
                }
            }
            is.close();
        } catch(IOException e) {
            Log.e("file", e.getMessage());
        }
        return temp;
    }

    public String getValueAppartenirNiv3(){
        String ligne;
        String temp = "";
        InputStream is = mCtx.getResources().openRawResource(R.raw.dataapp3);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            if (is!=null) {
                while ((ligne = reader.readLine()) != null) {
                    temp += ligne;
                }
            }
            is.close();
        } catch(IOException e) {
            Log.e("file", e.getMessage());
        }
        return temp;
    }

    public void close() {
        mDbHelper.close();
    }

    /*
        Récupère la liste des questions d'un contrat
        @Param idContrat : id du contrat dont on veut la liste de questions
    */
    public ArrayList<Question> getQuestionByContrat(int idContrat){
        Cursor c = mDb.query("contrat as c, appartenir as a, question", new String[]{"question._id", "question.libelleFR", "question.libelleEN", "question.libelleES",
                            "question.urlImgSol", "question.urlImg1", "question.urlImg2", "question.urlImg3" }, "(a.idQuestion=question._id AND c._id="+idContrat+" " +
                            "AND c._id = a.idContrat)", null, null, null, null);
        ArrayList<Question> lstQuestions = new ArrayList<Question>();
        while(c.moveToNext()){
            Question question = new Question(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
            lstQuestions.add(question);
        }
        return lstQuestions;
    }

    /*
        Récupèration d'un contrat spécifique avec sa liste de question
        @Param idContrat : contrat que l'on souhaite récupérer
    */
    public Contrat getContratById(int idContrat){
        Cursor c = mDb.query("contrat", new String[]{"_id, nbPoints, libelle, niveau, theme"}, "(_id ="+idContrat+")", null, null, null, null);
        Contrat contrat = null;
        while(c.moveToNext()){
            ArrayList<Question> lstQuestion = getQuestionByContrat(c.getInt(0));
            contrat = new Contrat(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), lstQuestion);
        }
        return contrat;
    }

    /*
        Récupération de la liste des contrats selon le niveau sélectionné
        @Param niveau : niveau selectionné
    */
    public ArrayList<Contrat> getContratsByNiveau(int niveau){
        Cursor c = mDb.query("contrat", new String[]{"_id, nbPoints, libelle, niveau, theme"}, "niveau="+niveau, null, null, null, null);
        ArrayList<Contrat> lstContrats = new ArrayList<Contrat>();
        while(c.moveToNext()){
            ArrayList<Question> lstQuestion = getQuestionByContrat(c.getInt(0));
            Contrat contrat = new Contrat(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), lstQuestion);
            lstContrats.add(contrat);
        }
        return lstContrats;
    }

    public Contrat getcontratByNiveauAndTheme(int niveau, int nbPointsContrat, String theme){
        Cursor c = mDb.query("contrat", new String[]{"_id, nbPoints, libelle, niveau, theme"}, "(niveau="+niveau+" AND theme='"+theme+"' AND nbPoints="+nbPointsContrat+")", null, null, null, null);
        Contrat contrat = null;
        while(c.moveToNext()){
            ArrayList<Question> lstQuestion = getQuestionByContrat(c.getInt(0));
            contrat = new Contrat(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), lstQuestion);
        }
        return contrat;
    }

    /*
        Récupère les gagnants d'un contrat spécifique
        @Param idContrat : id du contrat pour lequel on souhaite connaitre les gagnants
    */
    public Gagnant[] getGagnantsByIdContrat (int idContrat){
        Cursor c = mDb.query("gagnant", new String[]{"_id, score, pseudo, idContrat"}, "idContrat="+idContrat, null, null, null, "score desc", "10");
        ArrayList<Gagnant> lstGagnants = new ArrayList<Gagnant>();
        while(c.moveToNext()){
            Contrat contrat = getContratById(c.getInt(3));
            Gagnant gagnant = new Gagnant(c.getInt(0), c.getInt(1), c.getString(2), contrat);
            lstGagnants.add(gagnant);
        }
        Gagnant[] tabGagnants = new Gagnant[lstGagnants.size()];
        return lstGagnants.toArray(tabGagnants);

    }

    public void insertScore(String pseudo, int score, int idContrat){
        ContentValues val = new ContentValues();
        val.put("pseudo", pseudo);
        val.put("score", score);
        val.put("idContrat", idContrat);
        mDb.insert("gagnant", null, val);
    }

    /*******************************************************************************************/
    /*******************************************************************************************/
    /*                              Requetes Tests inserts                                     */
    /*******************************************************************************************/
    /*******************************************************************************************/

    /*
        Récupère la liste des contrats avec leurs liste de questions
        @test pour les inserts
    */
    public ArrayList<Contrat> getContrat(){
        Cursor c = mDb.query("contrat", new String[]{"_id, nbPoints, libelle, niveau, theme"}, null, null, null, null, null);
        ArrayList<Contrat> lstContrats = new ArrayList<Contrat>();
        while(c.moveToNext()){
            ArrayList<Question> lstQuestion = getQuestionByContrat(c.getInt(0));
            Contrat contrat = new Contrat(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), lstQuestion);
            lstContrats.add(contrat);
        }
        return lstContrats;
    }

    /*
        Récupère la liste des gagnants en base avec le contrat associé
        @test pour les inserts
    */
    public ArrayList<Gagnant> getGagnant(){
        Cursor c = mDb.query("gagnant", new String[]{"_id, score, pseudo, idContrat"}, null, null, null, null, null);
        ArrayList<Gagnant> lstGagnants = new ArrayList<Gagnant>();
        while(c.moveToNext()){
            Contrat contrat = getContratById(c.getInt(3));
            Gagnant gagnant = new Gagnant(c.getInt(0), c.getInt(1), c.getString(2), contrat);
            lstGagnants.add(gagnant);
        }
        return lstGagnants;
    }

    /*
       Récupère la liste des questions
       @test pour les inserts
    */
    public ArrayList<Question> getQuestion(){
        Cursor c = mDb.query("question", new String[]{"_id, libelleFR, libelleEN, question.libelleES, urlImgSol, urlImg1, urlImg2, urlImg3"}, null, null, null, null, null);
        ArrayList<Question> lstQuestions = new ArrayList<Question>();
        while(c.moveToNext()){
            Question question = new Question(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
            lstQuestions.add(question);
        }
        return lstQuestions;
    }

    /*
        Vérification de l'état de la table
        @test pour les inserts
    */
    public ArrayList<String> getAppartenir(){
        Cursor c = mDb.query("appartenir", new String[]{"idContrat", "idQuestion"}, null, null, null, null, null);
        ArrayList<String> appartenir = new ArrayList<String>();
        while(c.moveToNext()){
            appartenir.add(c.getString(0));
        }
        return appartenir;
    }
}

