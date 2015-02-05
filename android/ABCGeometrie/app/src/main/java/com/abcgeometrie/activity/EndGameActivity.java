package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.abcgeometrie.R;
import com.abcgeometrie.metier.Contrat;
import com.abcgeometrie.metier.DbAdapter;
import com.abcgeometrie.metier.Gagnant;
import com.abcgeometrie.metier.Jeu;

/**
 * Created by Yanick on 22/01/2015.
 */
public class EndGameActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView score, scorePts, scoreJoueur, nbQuestion, nbQuestionJoueur, newRecord, goScoreBoard, temps, tempsJoueur;
    private ImageView speak, home;
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
        score = (TextView) findViewById(R.id.score);
        scorePts = (TextView) findViewById(R.id.scorePts);
        scoreJoueur = (TextView) findViewById(R.id.scoreJoueur);
        nbQuestion = (TextView) findViewById(R.id.nbQuestion);
        nbQuestionJoueur = (TextView) findViewById(R.id.nbQuestionJoueur);
        newRecord = (TextView) findViewById(R.id.newRecord);
        temps = (TextView) findViewById(R.id.temps);
        tempsJoueur = (TextView) findViewById(R.id.tempsJoueur);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        saisiePseudo.setTypeface(tfLight);
        btnOk.setTypeface(tfMedium);
        score.setTypeface(tfLight);
        scorePts.setTypeface(tfMedium);
        scoreJoueur.setTypeface(tfMedium);
        nbQuestion.setTypeface(tfLight);
        temps.setTypeface(tfLight);
        tempsJoueur.setTypeface(tfLight);
        nbQuestionJoueur.setTypeface(tfLight);
        newRecord.setTypeface(tfMedium);

        // Vérification que le score soit dans les 10 premiers
        boolean nouveauRecord = true;
        final Jeu currentJeu = (Jeu) getIntent().getExtras().get("jeu");

        // Calcul des mauvaises réponses
        int mauvaiseReponse = currentJeu.getNbQuestionsNecessaires() - currentJeu.getNbQuestionsReussis();
        nbQuestionJoueur.setText(String.valueOf(mauvaiseReponse));

        // Mise au pluriel de la phrase "mauvaises réponses"
        if (mauvaiseReponse > 1)
            nbQuestion.setText(getResources().getString(R.string.nbQuestionPluriel));

        // Calcule du temps et affichage
        long tempsPasse = currentJeu.getTempsPasse();
        if(tempsPasse > 59){
            float tempsConvert = ((float) tempsPasse)/60;
            int minutes = (int) tempsConvert;
            int secondes = (int) tempsPasse-(60 * minutes);
            tempsJoueur.setText(String.valueOf(minutes)+"mn et "+String.valueOf(secondes)+"s");
        }else{
            tempsJoueur.setText(String.valueOf(tempsPasse)+"s");
        }

        // Gestion du nombre de question passées + affichage + saisie pseudo
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
                if(saisiePseudo.getText().toString().equals("")) {
                    saisiePseudo.setBackgroundColor(Color.RED);
                }else{
                    db.insertScore(saisiePseudo.getText().toString(), Integer.valueOf(scoreJoueur.getText().toString()), currentContrat.getId());
                    Intent i = new Intent(EndGameActivity.this, BoardActivity.class);
                    i.putExtra("contrat", currentContrat);
                    startActivity(i);
                }
            }
        });

        // Retour accueil
        home = (ImageView) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Remise du fond en blanc de l'édit text
        saisiePseudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saisiePseudo.setBackgroundColor(Color.WHITE);
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
                String tempsToString = currentJeu.tempsToString(getApplicationContext());
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,score.getText().toString() + scoreJoueur.getText().toString() + " ; " +
                        temps.getText().toString() + tempsToString + " ; "+
                        nbQuestion.getText().toString() + nbQuestionJoueur.getText().toString(),tts);
            }
        });

        // Drapeaux et event changement langue
        btnLang = (Button) findViewById(R.id.btnLang);
        btnLang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dl.onCreateDialog();
            }
        });

        // Changement état au click
        View[] views = {btnLang, home, speak};
        for (View btn : views){
            btn.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //v.setLayoutParams(resize(v));
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setAlpha((float) 0.7);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        v.setAlpha((float) 1);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        final Dialog dial = new Dialog(this, android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(220);
        dial.getWindow().setBackgroundDrawable(d);
        dial.setContentView(R.layout.actvity_dialogreturn);
        ImageView backHome = (ImageView) dial.findViewById(R.id.backHome);
        backHome.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity( new Intent(EndGameActivity.this, MainActivity.class));
            }
        });
        ImageView backGame = (ImageView) dial.findViewById(R.id.backGame);
        backGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dial.cancel();
            }
        });
        RelativeLayout rl = (RelativeLayout) dial.findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dial.cancel();
            }
        });
        dial.show();
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

