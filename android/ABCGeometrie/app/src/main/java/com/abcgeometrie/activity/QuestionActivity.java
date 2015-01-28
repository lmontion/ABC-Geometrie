package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcgeometrie.R;
import com.abcgeometrie.metier.Question;

public class QuestionActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewQuestion;
    private Button btnLang;
    private ImageView speak;
    private TextToSpeech tts;
    private ImageButton rep, img1, img2, img3;
    private DialogLang dl;
    private String lang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_haut, R.anim.slide_bas);

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(QuestionActivity.this);

        // Récupération bouton et evenement
        rep = (ImageButton) findViewById(R.id.rep1);
        img1 = (ImageButton) findViewById(R.id.rep2);
        img2 = (ImageButton) findViewById(R.id.rep3);
        img3 = (ImageButton) findViewById(R.id.rep4);
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuestionActivity.this, EndGameActivity.class);
                startActivity(i);
            }
        });

        // Application de la police
        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewQuestion.setTypeface(tfLight);


        // Récupération langue en cours + event speaker
        tts = new TextToSpeech(this, this);
        speak = (ImageView) findViewById(R.id.btnTTS);
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewQuestion.getText().toString(),tts);
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
