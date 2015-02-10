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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.abcgeometrie.R;

/**
 * Created by Yanick on 22/01/2015.
 */
public class ChooseThemeActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewChooseTheme;
    private Button btnThemeColor, btnThemeForm, btnThemeColorAndForm,btnLang;
	private ImageButton arrowThemeColor, arrowThemeForm, arrowThemeColorAndForm;
    private ImageView speak, home;
    private TextToSpeech tts;
    private String lang = "";
    private DialogLang dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_theme);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        if (getIntent().getExtras() == null) {
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(ChooseThemeActivity.this);

        // Récupération des Boutons choix du theme + event
		arrowThemeColor = (ImageButton) findViewById(R.id.flecheGauche);
        arrowThemeForm = (ImageButton) findViewById(R.id.flecheCentre);
        arrowThemeColorAndForm = (ImageButton) findViewById(R.id.flecheDroite);
        btnThemeColor = (Button) findViewById(R.id.btnThemeColor);
        btnThemeForm = (Button) findViewById(R.id.btnThemeForm);
        btnThemeColorAndForm = (Button) findViewById(R.id.btnThemeColorAndForm);
        btnThemeColor.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, ChooseDefiActivity.class);
                i.putExtra("lvl",1);
                i.putExtra("theme","couleurs");
                startActivity(i);
                finish();
            }
        });
		 arrowThemeColor.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, ChooseDefiActivity.class);
                i.putExtra("lvl",1);
                i.putExtra("theme","couleurs");
                startActivity(i);
                finish();
                }
        });
        btnThemeForm.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, ChooseDefiActivity.class);
                i.putExtra("lvl",1);
                i.putExtra("theme","formes");
                startActivity(i);
                finish();
            }
        });
        arrowThemeForm.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, ChooseDefiActivity.class);
                i.putExtra("lvl",1);
                i.putExtra("theme","formes");
                startActivity(i);
                finish();
            }
        });
        btnThemeColorAndForm.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, ChooseDefiActivity.class);
                i.putExtra("lvl",1);
                i.putExtra("theme","couleurs et formes");
                startActivity(i);
                finish();
            }
        });
        arrowThemeColorAndForm.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, ChooseDefiActivity.class);
                i.putExtra("lvl",1);
                i.putExtra("theme","couleurs et formes");
                startActivity(i);
                finish();
            }
        });

        // Application de la police
        txtViewChooseTheme = (TextView) findViewById(R.id.txtViewChooseTheme);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewChooseTheme.setTypeface(tfLight);
        btnThemeColor.setTypeface(tfMedium);
        btnThemeForm.setTypeface(tfMedium);
        btnThemeColorAndForm.setTypeface(tfMedium);

        // Retour accueil
        home = (ImageView) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0,R.anim.fade_out);
            }
        });

        // Récupération langue en cours + event speaker
        tts = new TextToSpeech(this,this);
        speak = (ImageView) findViewById(R.id.btnTTS);
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewChooseTheme.getText().toString(),tts);
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
        View[] views = {btnThemeColor, btnThemeForm, btnThemeColorAndForm, arrowThemeColor, arrowThemeForm, arrowThemeColorAndForm, btnLang, home, speak};
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
        Intent intent = new Intent(ChooseThemeActivity.this, ChooseLevelActivity.class);
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
