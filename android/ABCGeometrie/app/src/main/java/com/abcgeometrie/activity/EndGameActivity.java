package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.abcgeometrie.R;


/**
 * Created by Yanick on 22/01/2015.
 */
public class EndGameActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewBravo, score, scoreJoueur, temps, tempsJoueur, nbQuestion, nbQuestionJoueur, newRecord, pseudo;
    private ImageView speak;
    private Button btnOk;
    private EditText saisiePseudo;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        saisiePseudo = (EditText) findViewById(R.id.saisiePseudo);
        btnOk = (Button) findViewById(R.id.btnOk);
        txtViewBravo = (TextView) findViewById(R.id.txtViewBravo);
        tts = new TextToSpeech(this,this);
        speak = (ImageView) findViewById(R.id.btnTTS);
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



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EndGameActivity.this, BoardActivity.class);
                startActivity(i);
            }
        });

        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech("FR",txtViewBravo.getText().toString(),tts);
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
    public void onInit(int status) {

    }
}
