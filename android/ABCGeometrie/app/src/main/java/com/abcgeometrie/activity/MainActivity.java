package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcgeometrie.R;
import com.abcgeometrie.metier.DbAdapter;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ImageButton btnTeam, btnAlizaza;
    private RelativeLayout relativeLayout;
    private TextView abc, dela, geo1, geo2, baseLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        abc = (TextView) findViewById(R.id.textViewAbc);
        dela = (TextView) findViewById(R.id.textViewDeLa);
        geo1 = (TextView) findViewById(R.id.textViewGeometrie01);
        geo2 = (TextView) findViewById(R.id.textViewGeometrie02);
        btnAlizaza = (ImageButton) findViewById(R.id.btnAlizaza);
        baseLine = (TextView) findViewById(R.id.textViewBaseLine);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutHome);
        btnTeam = (ImageButton) findViewById(R.id.btnIUT);

        abc.setTypeface(tfLight);
        dela.setTypeface(tfLight);
        geo1.setTypeface(tfLight);
        geo2.setTypeface(tfLight);
        baseLine.setTypeface(tfLight);


        DbAdapter db = new DbAdapter(this);
        db.open();
        // Tests insert
        /*ArrayList<String> contrats = db.getContrat();
        ArrayList<String> gagnants = db.getGagnant();
        ArrayList<String> question = db.getQuestion();
        ArrayList<String> appartenir = db.getAppartenir();*/
        // Récupération menu contrat (lvl 2 ou 3)
        //ArrayList<String> contratsByNiveau = db.getContratsByNiveau(2);
        // Récupération menu theme (lvl 1)
        //ArrayList<String> themesContrats = db.getThemeByNiveau(1);
        // Récupération des contrats selon le niveau et le theme choisis (lvl 1)
        //ArrayList<String> contratsByNiveauAndTheme = db.getcontratsByNiveauAndTheme(1,"couleurs");
        // Récupération des questions selon le contrat choisit
        //ArrayList<String> getQuestionByContrat = db.getQuestionByContrat(15);
        // Récupération du nombre de bonne réponse nécessaire à la validation du contrat
        //ArrayList<String> getNbPointByContrat = db.getNbPointByContrat(1);
        // Récupération des 10 premiers meilleurs score
        //ArrayList<String> getScoreByContrat = db.getScoreByContrat(15);
        // Insertion d'un nouveau meilleure score si score meilleur que les 10 sortis
        //db.insertScore("Pierre", 800, 15);
        // Vérification
        //ArrayList<String> getScoreByContrat = db.getScoreByContrat(15);


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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.fade_out);
    }
}
