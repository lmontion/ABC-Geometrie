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
 * Created by Yanick on 22/01/2015.
 */
public class AlizazaActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewAlizaza, activiteAlizaza, txtViewSiteAlizaza;
    private ImageView speak, home;
    private Button btnLang;
    private TextToSpeech tts;
    private DialogLang dl;
    private String lang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alizaza);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.inverse_bas, R.anim.inverse_haut);

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(AlizazaActivity.this);

        // Application de la police
        txtViewAlizaza = (TextView) findViewById(R.id.txtViewAlizaza);
        activiteAlizaza = (TextView) findViewById(R.id.txtActiviteAlizaza);
        txtViewSiteAlizaza = (TextView) findViewById(R.id.txtViewSiteAlizaza);
        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewSiteAlizaza.setTypeface(tfLight);
        activiteAlizaza.setTypeface(tfLight);
        txtViewAlizaza.setTypeface(tfLight);

        // Retour accueil
        home = (ImageView) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlizazaActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Récupération langue en cours + event speaker
        tts = new TextToSpeech(this,this);
        speak = (ImageView) findViewById(R.id.btnTTS);
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewAlizaza.getText().toString()+";"+activiteAlizaza.getText().toString(),tts);
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
    public void onInit(int status) {}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AlizazaActivity.this, MainActivity.class);
        intent.putExtra("retour", true);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if(tts != null)
            tts.shutdown();
        super.onDestroy();
    }
}
