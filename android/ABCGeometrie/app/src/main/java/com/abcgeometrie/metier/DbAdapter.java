package com.abcgeometrie.metier;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {

    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String CREATE_TABLE_CONTRAT =
            "create table contrat (_id integer primary key autoincrement, libelle text not null, nbPoints int, niveau text);";
    private static final String CREATE_TABLE_GAGNANT =
            "create table gagnant (_id integer primary key autoincrement, pseudo text not null, score integer);";
    private static final String CREATE_TABLE_QUESTION =
            "create table question (_id integer primary key autoincrement, libelleFR text, libelleAN text, libelleFR text," +
                    " urlImgSol text, urlImg1 text, urlImg2 text, urlImg3 text, theme text);";
    private static final String CREATE_TABLE_APPARTENIR =
            "create table appartenir (idContrat primary key, idQuestion primary key);";


    private static final String DATABASE_NAME = "ABC_Geometrie";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CONTRAT);
            db.execSQL(CREATE_TABLE_CONTRAT);
            db.execSQL(CREATE_TABLE_QUESTION);
            db.execSQL(CREATE_TABLE_APPARTENIR);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS utilisateur");
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
}

