package com.abcgeometrie.activity;

import android.app.Activity;
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
import com.abcgeometrie.metier.DbAdapter;
import com.abcgeometrie.metier.Gagnant;

/**
 * Created by Yanick on 22/01/2015.
 */
public class EndGameActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewBravo, score, scoreJoueur, temps, tempsJoueur, nbQuestion, nbQuestionJoueur, newRecord, pseudo, goScoreBoard;
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

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(EndGameActivity.this);

        // Validation du nouveau record
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saisiePseudo.getText().toString().equals("")){
                    saisiePseudo.setBackgroundColor(R.color.monRouge);
                }else{
                    Intent i = new Intent(EndGameActivity.this, BoardActivity.class);
                    // TODO : faire passer le contrat
                    i.putExtra("pseudo",saisiePseudo.getText().toString());
                    i.putExtra("score", scoreJoueur.getText().toString());
                    startActivity(i);
                }
            }
        });

        // Application de la police
        saisiePseudo = (EditText) findViewById(R.id.saisiePseudo);
        txtViewBravo = (TextView) findViewById(R.id.txtViewBravo);
        score = (TextView) findViewById(R.id.score);
        scoreJoueur = (TextView) findViewById(R.id.scoreJoueur);
        temps = (TextView) findViewById(R.id.temps);
        tempsJoueur = (TextView) findViewById(R.id.tempsJoueur);
        nbQuestion = (TextView) findViewById(R.id.nbQuestion);
        nbQuestionJoueur = (TextView) findViewById(R.id.nbQuestionJoueur);
        newRecord = (TextView) findViewById(R.id.newRecord);
        pseudo = (TextView) findViewById(R.id.pseudo);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        saisiePseudo.setTypeface(tfLight);
        btnOk.setTypeface(tfMedium);
        txtViewBravo.setTypeface(tfLight);
        score.setTypeface(tfLight);
        scoreJoueur.setTypeface(tfLight);
        temps.setTypeface(tfLight);
        tempsJoueur.setTypeface(tfLight);
        nbQuestion.setTypeface(tfLight);
        nbQuestionJoueur.setTypeface(tfLight);
        pseudo.setTypeface(tfLight);
        newRecord.setTypeface(tfMedium);

        // Vérification que le score soit dans les 10 premiers
        boolean nouveauRecord = false;
        int sj = Integer.parseInt(scoreJoueur.getText().toString());
        db = new DbAdapter(this);
        db.open();
        Gagnant[] lesGagnants = db.getGagnantsByIdContrat(15);
        for(int i = 0; i<lesGagnants.length; i++){
            if(lesGagnants[i].getScore() < sj){
                nouveauRecord = true;
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
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.slide_out_return);
    }

    @Override
    public void onInit(int status) {}
}

