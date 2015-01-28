package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.abcgeometrie.R;

public class ChooseDefiActivity extends Activity implements TextToSpeech.OnInitListener {

    private Button btnLang, btn10, btn20, btn40;
    private TextView tv;
    private ImageView speak, home;
    private TextToSpeech tts;
    private DialogLang dl;
    private String lang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_defi);

        //Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(ChooseDefiActivity.this);

        // Récupération des Boutons choix du contrat + event
        btn10 = (Button) findViewById(R.id.btn10);
        btn20 = (Button) findViewById(R.id.btn20);
        btn40 = (Button) findViewById(R.id.btn40);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, QuestionActivity.class);
                startActivity(i);
            }
        });
        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, QuestionActivity.class);
                startActivity(i);
            }
        });
        btn40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, QuestionActivity.class);
                startActivity(i);
            }
        });

        // Application de la police
        tv = (TextView) findViewById(R.id.textChooseDefi);
        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        tv.setTypeface(tfLight);
        btn10.setTypeface(tfMedium);
        btn20.setTypeface(tfMedium);
        btn40.setTypeface(tfMedium);

        // Retour accueil
        home = (ImageView) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, MainActivity.class);
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
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,tv.getText().toString(),tts);
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
        overridePendingTransition(0,R.anim.slide_haut_return);
    }

    @Override
    public void onInit(int status) {}
}
