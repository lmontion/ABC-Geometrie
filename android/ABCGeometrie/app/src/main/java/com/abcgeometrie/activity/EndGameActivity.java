package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.abcgeometrie.R;
import com.abcgeometrie.metier.Contrat;
import com.abcgeometrie.metier.DbAdapter;
import com.abcgeometrie.metier.Gagnant;
import com.abcgeometrie.metier.Jeu;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Yanick on 22/01/2015.
 */
public class EndGameActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewBravo, score, scoreJoueur, nbQuestion, nbQuestionJoueur, newRecord, pseudo, goScoreBoard, temps, tempsJoueur;
    private ImageView speak;
    private Button btnOk, btnLang;
    private EditText saisiePseudo;
    private TextToSpeech tts;
    private DialogLang dl;
    private String lang = "";
    protected DbAdapter db;
    private LinearLayout layoutSaisieNewRecord, layoutNewRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Application de la police
        saisiePseudo = (EditText) findViewById(R.id.saisiePseudo);
        btnOk = (Button) findViewById(R.id.btnOk);
        txtViewBravo = (TextView) findViewById(R.id.txtViewBravo);
        score = (TextView) findViewById(R.id.score);
        scoreJoueur = (TextView) findViewById(R.id.scoreJoueur);
        nbQuestion = (TextView) findViewById(R.id.nbQuestion);
        nbQuestionJoueur = (TextView) findViewById(R.id.nbQuestionJoueur);
        newRecord = (TextView) findViewById(R.id.newRecord);
        pseudo = (TextView) findViewById(R.id.pseudo);
        temps = (TextView) findViewById(R.id.temps);
        tempsJoueur = (TextView) findViewById(R.id.tempsJoueur);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        saisiePseudo.setTypeface(tfLight);
        btnOk.setTypeface(tfMedium);
        txtViewBravo.setTypeface(tfLight);
        score.setTypeface(tfLight);
        scoreJoueur.setTypeface(tfLight);
        nbQuestion.setTypeface(tfLight);
        temps.setTypeface(tfLight);
        tempsJoueur.setTypeface(tfLight);
        nbQuestionJoueur.setTypeface(tfLight);
        pseudo.setTypeface(tfLight);
        newRecord.setTypeface(tfMedium);

        // Vérification que le score soit dans les 10 premiers
        boolean nouveauRecord = true;
        Jeu currentJeu = (Jeu) getIntent().getExtras().get("jeu");
        nbQuestionJoueur.setText(String.valueOf(currentJeu.getNbQuestionsNecessaires()));
        long tempsPasse = currentJeu.getTempsPasse()+100;
        if(tempsPasse > 59){
            float tempsConvert = ((float) tempsPasse)/60;
            int minutes = (int) tempsConvert;
            int secondes = (int) tempsPasse-(60 * minutes);
            tempsJoueur.setText(String.valueOf(minutes)+"mn et "+String.valueOf(secondes)+"s");
        }else{
            tempsJoueur.setText(String.valueOf(tempsPasse)+"s");
        }
        int sj = currentJeu.calculScore();
        scoreJoueur.setText(String.valueOf(sj));
        final Contrat currentContrat = (Contrat) getIntent().getExtras().get("contrat");
        db = new DbAdapter(this);
        db.open();
        Gagnant[] lesGagnants = db.getGagnantsByIdContrat(currentContrat.getId());
        int compteurScore = 0;
        for(int i = 0; i<lesGagnants.length; i++){
            if(lesGagnants[i].getScore() > sj){
                compteurScore++;
            }
            if(compteurScore == 10){
                nouveauRecord = false;
            }
        }
        layoutSaisieNewRecord = (LinearLayout) findViewById(R.id.layoutSaisieNewRecord);
        layoutNewRecord = (LinearLayout) findViewById(R.id.layoutNewRecord);
        goScoreBoard = (TextView) findViewById(R.id.goScoreBoard);
        if(nouveauRecord == false){
            layoutNewRecord.setVisibility(View.INVISIBLE);
            layoutSaisieNewRecord.setVisibility(View.INVISIBLE);
            goScoreBoard.setVisibility(View.VISIBLE);
        } else {
            layoutNewRecord.setVisibility(View.VISIBLE);
            layoutSaisieNewRecord.setVisibility(View.VISIBLE);
            goScoreBoard.setVisibility(View.INVISIBLE);
        }

        // Event passage au tablau des scores sans nouveau record
        goScoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EndGameActivity.this, BoardActivity.class);
                startActivity(i);
            }
        });

        // Validation du nouveau record
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saisiePseudo.getText().toString().equals("")){
                    saisiePseudo.setBackgroundColor(R.color.monRouge);
                }else{
                    db.insertScore(saisiePseudo.getText().toString(), Integer.valueOf(scoreJoueur.getText().toString()), currentContrat.getId());
                    Intent i = new Intent(EndGameActivity.this, BoardActivity.class);
                    i.putExtra("contrat", currentContrat);
                    startActivity(i);
                }
            }
        });

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(EndGameActivity.this, currentContrat, currentJeu);

        // Récupération langue en cours + event speaker
        tts = new TextToSpeech(this,this);
        speak = (ImageView) findViewById(R.id.btnTTS);
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewBravo.getText().toString(),tts);
            }
        });

        // Drapeaux et event changement langue
        btnLang = (Button) findViewById(R.id.btnLang);
        btnLang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dl.onCreateDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aller a l'accueil");
        DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        DialogInterface.OnClickListener annuler = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        };
        builder.setPositiveButton("OK", ok);
        builder.setNegativeButton("Annuler", null);
        builder.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.slide_out_return);
    }

    @Override
    public void onInit(int status) {}
}

