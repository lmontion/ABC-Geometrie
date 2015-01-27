package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.abcgeometrie.R;
import com.abcgeometrie.metier.Contrat;
import com.abcgeometrie.metier.DbAdapter;
import com.abcgeometrie.metier.Gagnant;
import com.abcgeometrie.metier.Question;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ImageButton btnTeam, btnAlizaza;
    private RelativeLayout relativeLayout;
    private TextView abc, dela, geo1, geo2, baseLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Récupération des items et events associés
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutHome);
        btnAlizaza = (ImageButton) findViewById(R.id.btnAlizaza);
        btnTeam = (ImageButton) findViewById(R.id.btnIUT);
        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TeamActivity.class);
                startActivity(i);
            }
        });
        btnAlizaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AlizazaActivity.class);
                startActivity(i);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChooseLevelActivity.class);
                startActivity(i);
            }
        });

        // Application de la police
        abc = (TextView) findViewById(R.id.textViewAbc);
        dela = (TextView) findViewById(R.id.textViewDeLa);
        geo1 = (TextView) findViewById(R.id.textViewGeometrie01);
        geo2 = (TextView) findViewById(R.id.textViewGeometrie02);
        baseLine = (TextView) findViewById(R.id.textViewBaseLine);
        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        abc.setTypeface(tfLight);
        dela.setTypeface(tfLight);
        geo1.setTypeface(tfLight);
        geo2.setTypeface(tfLight);
        baseLine.setTypeface(tfLight);

        // Test avec les requetes
        DbAdapter db = new DbAdapter(this);
        db.open();
        // Tests insert
        //ArrayList<Contrat> lstContrats = db.getContrat();
        //ArrayList<Gagnan> lstGagnants = db.getGagnant();
        ArrayList<Question> lstQuestions = db.getQuestion();
        //ArrayList<String> appartenir = db.getAppartenir();
        // Récupération des 10 premiers gagnant d'un contrat avec classes
        ArrayList<Gagnant> lstGagnants = db.getGagnantsByIdContrat(15);
        // Récupération des contrats selon le niveau selectionné avec classes
        ArrayList<Contrat> lstContrats = db.getContratsByNiveau(2);
        // Récupération des contrats selon un niveau et un theme avec classes
        ArrayList<Contrat> lstContrats2 = db.getcontratsByNiveauAndTheme(1, "couleurs");
        // Récupération d'un contrat selon son id avec classes
        Contrat contrat = db.getContratById(2);
        // Insertion d'un nouveau meilleure score si score meilleur que les 10 sortis
        //db.insertScore("Paul", 850, 15);
        // Vérification
        //ArrayList<Gagnant> lstGagnants = db.getGagnantsByIdContrat(15);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.fade_out);
    }
}
