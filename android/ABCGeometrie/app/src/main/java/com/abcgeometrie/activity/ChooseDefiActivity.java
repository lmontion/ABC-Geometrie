package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.view.MotionEvent;
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
    private String currentTheme;
    private int currentLvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_defi);

        //Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Récupération des variables concernant le theme et le niveau selectionnés précedemment
        currentTheme = (String) getIntent().getExtras().get("theme");
        currentLvl = (int) getIntent().getExtras().get("lvl");

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(ChooseDefiActivity.this, currentTheme, currentLvl);

        // Récupération des Boutons choix du contrat + event
        btn10 = (Button) findViewById(R.id.btn10);
        btn20 = (Button) findViewById(R.id.btn20);
        btn40 = (Button) findViewById(R.id.btn40);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, QuestionActivity.class);
                i.putExtra("nbPoints",10);
                i.putExtra("theme", currentTheme);
                i.putExtra("lvl", currentLvl);
                startActivity(i);
            }
        });
        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, QuestionActivity.class);
                i.putExtra("nbPoints",20);
                i.putExtra("theme", currentTheme);
                i.putExtra("lvl", currentLvl);
                startActivity(i);
            }
        });
        btn40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, QuestionActivity.class);
                i.putExtra("nbPoints",40);
                i.putExtra("theme", currentTheme);
                i.putExtra("lvl", currentLvl);
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

        // Changement d'état au click
        View[] views = {btn10, btn20, btn40, btnLang, home, speak};
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
        overridePendingTransition(0,R.anim.slide_haut_return);
    }

    @Override
    public void onInit(int status) {}

    @Override
    public void onBackPressed() {
        // Redirection avec le bouton retour selon le niveau selectionné
        if (currentLvl == 1){
            Intent intent = new Intent(ChooseDefiActivity.this, ChooseThemeActivity.class);
            intent.putExtra("retour", true);
            startActivity(intent);
        }
        if (currentLvl == 2 || currentLvl == 3){
            Intent intent = new Intent(ChooseDefiActivity.this, ChooseLevelActivity.class);
            intent.putExtra("retour", true);
            startActivity(intent);
        }
    }
}
