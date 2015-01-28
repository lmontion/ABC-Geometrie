package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

import com.abcgeometrie.R;

public class ChooseLevelActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewLvl2, txtViewLvl3, txtViewLvl1, txtViewSelectLvl;
    private ImageView speak, home;
    private TextToSpeech tts;
    private Button btnLang;
    private String lang = "";
    private DialogLang dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(ChooseLevelActivity.this);

        // Récupération des textView choix des niveaux + event
        txtViewLvl3 = (TextView) findViewById(R.id.txtViewLvl3);
        txtViewLvl2 = (TextView) findViewById(R.id.txtViewLvl2);
        txtViewLvl1 = (TextView) findViewById(R.id.txtViewLvl1);
        txtViewLvl1.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseLevelActivity.this, ChooseThemeActivity.class);
                startActivity(i);
            }
        });
        txtViewLvl2.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseLevelActivity.this, ChooseDefiActivity.class);
                i.putExtra("lvl",2);
                i.putExtra("theme","");
                startActivity(i);
            }
        });
        txtViewLvl3.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseLevelActivity.this, ChooseDefiActivity.class);
                i.putExtra("lvl",3);
                i.putExtra("theme","");
                startActivity(i);
            }
        });

        // Application de la police
        txtViewSelectLvl = (TextView) findViewById(R.id.txtViewSelectLvl);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewSelectLvl.setTypeface(tfLight);
        txtViewLvl1.setTypeface(tfMedium);
        txtViewLvl2.setTypeface(tfMedium);
        txtViewLvl3.setTypeface(tfMedium);

        // Retour accueil
        home = (ImageView) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseLevelActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(0, R.anim.fade_out);
            }
        });

        // Récupération langue en cours + event speaker
        tts = new TextToSpeech(this,this);
        speak = (ImageView) findViewById(R.id.btnTTS);
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewSelectLvl.getText().toString(),tts);
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
