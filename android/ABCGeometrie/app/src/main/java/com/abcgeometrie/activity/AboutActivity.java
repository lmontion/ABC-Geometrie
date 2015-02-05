package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.abcgeometrie.R;

/**
 * Created by Yanick on 03/02/2015.
 */
public class AboutActivity extends Activity implements TextToSpeech.OnInitListener{

    private ImageView speak, home;
    private Button btnLang;
    private TextToSpeech tts;
    private DialogLang dl;
    private String lang = "";
    private TextView txtViewAbout, txtAbout1, txtAbout2, txtAbout3, txtAbout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.inverse_bas, R.anim.inverse_haut);

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(AboutActivity.this);

        // Application de la police
        txtViewAbout = (TextView) findViewById(R.id.txtViewAbout);
        txtAbout1 = (TextView) findViewById(R.id.txtAbout1);
        txtAbout2 = (TextView) findViewById(R.id.txtAbout2);
        txtAbout3 = (TextView) findViewById(R.id.txtAbout3);
        txtAbout4 = (TextView) findViewById(R.id.txtAbout4);
        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        txtViewAbout.setTypeface(tfLight);
        txtAbout1.setTypeface(tfLight);
        txtAbout2.setTypeface(tfLight);
        txtAbout3.setTypeface(tfLight);
        txtAbout4.setTypeface(tfLight);

        // Retour accueil
        home = (ImageView) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutActivity.this, MainActivity.class);
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
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang, txtAbout1.getText().toString() + " ; " +
                        txtAbout2.getText().toString() + " ;  " + txtAbout3.getText().toString() + " ; " + txtAbout4.getText().toString() , tts);
            }
        });

        // Drapeaux et event changement langue
        btnLang = (Button) findViewById(R.id.btnLang);
        btnLang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dl.onCreateDialog();
            }
        });

        // Changement d'état au click
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
    public void onInit(int status) {}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AboutActivity.this, ChooseLevelActivity.class);
        intent.putExtra("retour", true);
        startActivity(intent);
    }
}
