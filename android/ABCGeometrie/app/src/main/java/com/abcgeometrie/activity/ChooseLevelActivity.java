package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

import com.abcgeometrie.R;

public class ChooseLevelActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewLvl2, txtViewLvl3, txtViewLvl1, txtViewSelectLvl;
    private ImageView speak, home, btnAbout;
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
        if (getIntent().getExtras() == null) {
            //overridePendingTransition(0,0);
        }

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(ChooseLevelActivity.this);

        // Récupération des textView choix des niveaux + event
        txtViewLvl3 = (TextView) findViewById(R.id.txtViewLvl3);
        txtViewLvl2 = (TextView) findViewById(R.id.txtViewLvl2);
        txtViewLvl1 = (TextView) findViewById(R.id.txtViewLvl1);
        txtViewLvl1.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_return));
                Intent i = new Intent(ChooseLevelActivity.this, ChooseThemeActivity.class);
                startActivity(i);

            }
        });
        /*txtViewLvl1.setOnTouchListener(new View.OnTouchListener() {

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
        });*/
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

        // Acces à la page about
        btnAbout = (ImageView) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseLevelActivity.this, AboutActivity.class);
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

        View[] views = {txtViewLvl1, txtViewLvl2, txtViewLvl3, btnAbout, btnLang, home, speak};
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
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.slide_out_return);
    }

    @Override
    public void onInit(int status) {}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChooseLevelActivity.this, MainActivity.class);
        intent.putExtra("retour", true);
        startActivity(intent);
    }

    /*private ViewGroup.LayoutParams resize(View v){
        double newWidth = v.getWidth() * 1.1;
        double newHeight = v.getHeight() * 1.1;
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = (int)newWidth;
        params.height = (int)newHeight;
        return params;
    }*/
}
