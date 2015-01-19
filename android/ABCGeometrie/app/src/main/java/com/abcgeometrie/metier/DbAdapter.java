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

    private static final String CREATE_TABLE_UTILISATEUR =
            "create table utilisateur (_id integer primary key autoincrement, login text not null, mdp text, admin boolean);";
    private static final String CREATE_TABLE_QUESTION =
            "create table question (_id integer primary key autoincrement, libelle text not null, bonneImage text, mauvaiseImage1 text, mauvaiseImage2 text, mauvaiseImage3 text);";
    private static final String CREATE_TABLE_SONDAGE =
            "create table sondage (_id integer primary key autoincrement, libelle text not null, point integer);";
    private static final String CREATE_TABLE_NIVEAU =
            "create table niveau (_id integer primary key autoincrement, libelle text not null);";
    private static final String CREATE_TABLE_SOUSCRIRE =
            "create table souscrire (idUser integer primary key,idContrat primary key, tempsPasse integer not null, nbQuestionsPosees integer);";
    private static final String CREATE_TABLE_APPARTENIR =
            "create table appartenir (idContrat integer primary key, idQuestion integer primary key);";

    private static final String DATABASE_NAME = "ABCGeometrie";
    private static final String DATABASE_TABLE_UTILISATEUR = "utilisateur";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_UTILISATEUR);
            db.execSQL(CREATE_TABLE_QUESTION);
            db.execSQL(CREATE_TABLE_SONDAGE);
            db.execSQL(CREATE_TABLE_NIVEAU);
            db.execSQL(CREATE_TABLE_SOUSCRIRE);
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

