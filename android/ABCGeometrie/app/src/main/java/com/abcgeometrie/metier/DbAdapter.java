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
import java.io.DataInputStream;
import java.io.FileInputStream;
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

    /*
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
                    "('Contrat 40 points', 40, 3, '');";*/

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


    private static final String DATABASE_NAME = "ABC_Geometrie";
    private static final int DATABASE_VERSION = 10;
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
            /*db.execSQL(INSERT_TABLE_QUESTION);
            db.execSQL(INSERT_TABLE_APPARTENIR);*/
            db.execSQL(INSERT_TABLE_QUESTION_LVL1);
            db.execSQL(INSERT_TABLE_APPARTENIR_LVL1);
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
    public ArrayList<Question> getQuestionByNiveauAndContrat(int niveau, int nbPointsContrat){
        Cursor c = mDb.query("contrat as c, appartenir as a, question",
                new String[]{"question._id", "question.libelleFR", "question.libelleEN", "question.libelleES",
                "question.urlImgSol", "question.urlImg1", "question.urlImg2", "question.urlImg3" },
                "(a.idQuestion=question._id AND c._id = a.idContrat AND niveau="+niveau+" AND nbPoints="+nbPointsContrat+")"
                , null, null, null, null);
        ArrayList<Question> lstQuestions = new ArrayList<Question>();
        while(c.moveToNext()){
            Question question = new Question(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
            lstQuestions.add(question);
        }
        return lstQuestions;
    }


    public ArrayList<> getQuestionByNiveauAndContrat(int niveau, int nbPointsContrat, String theme){
        Cursor c = mDb.query("contrat as c, appartenir as a, question",
                new String[]{"question._id", "question.libelleFR", "question.libelleEN", "question.libelleES",
                        "question.urlImgSol", "question.urlImg1", "question.urlImg2", "question.urlImg3" },
                "(a.idQuestion=question._id AND c._id = a.idContrat AND niveau="+niveau+" AND nbPoints="+nbPointsContrat+" AND theme="+theme+")"
                , null, null, null, null);
        ArrayList<Question> lstQuestions = new ArrayList<Question>();
        while(c.moveToNext()){
            Question question = new Question(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
            lstQuestions.add(question);
        }
        return lstQuestions;
    }*/

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

    /*
        Récupère la liste des contrats selon le niveau selectionné ainsi que le theme
        @Param niveau : niveau selectionné par le joueur
        @Param theme : theme selectionné par le joueur
    */

   /* public Contrat getcontratByNiveauAndTheme(int niveau, int nbPointsContrat){
        Cursor c = mDb.query("contrat", new String[]{"_id, nbPoints, libelle, niveau, theme"}, "(niveau="+niveau+" AND nbPoints="+nbPointsContrat+")", null, null, null, null);
        //ArrayList<Contrat> lstContrats = new ArrayList<Contrat>();
        Contrat contrat = null;
        while(c.moveToNext()){
            ArrayList<Question> lstQuestion = getQuestionByContrat(c.getInt(0));
            contrat = new Contrat(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), lstQuestion);
            //lstContrats.add(contrat);
        }
        return contrat;
    }
*/
    public Contrat getcontratByNiveauAndTheme(int niveau, int nbPointsContrat, String theme){
        Cursor c = mDb.query("contrat", new String[]{"_id, nbPoints, libelle, niveau, theme"}, "(niveau="+niveau+" AND theme='"+theme+"' AND nbPoints="+nbPointsContrat+")", null, null, null, null);
        //ArrayList<Contrat> lstContrats = new ArrayList<Contrat>();
        Contrat contrat = null;
        while(c.moveToNext()){
            ArrayList<Question> lstQuestion = getQuestionByContrat(c.getInt(0));
            contrat = new Contrat(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), lstQuestion);
            //lstContrats.add(contrat);
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

    /*
        Insertion d'un nouveau gagnant selon son pseudo, son score et l'id du contrat effectué
        @Param pseudo : pseudo indiqué par le joueur
        @Param score : score effectué a la fin du contrat
        @Param idContrat : id du contrat effectué
    */
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

