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
import com.abcgeometrie.R;

/**
 * Created by Yanick on 21/01/2015.
 */
public class TeamActivity extends Activity implements TextToSpeech.OnInitListener {

    private TextView txtViewTeam, txtTristant, txtManon, txtYanick, txtLucas, txtViewDawin;
    private ImageView speak, home;
    private Button btnLang;
    private TextToSpeech tts;
    private DialogLang dl;
    private String lang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.inverse_bas, R.anim.inverse_haut);

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(TeamActivity.this);

        // Application de la police
        txtViewTeam = (TextView) findViewById(R.id.txtViewTeam);
        txtTristant = (TextView) findViewById(R.id.txtTristant);
        txtManon = (TextView) findViewById(R.id.txtManon);
        txtYanick = (TextView) findViewById(R.id.txtYanick);
        txtLucas = (TextView) findViewById(R.id.txtLucas);
        txtViewDawin = (TextView) findViewById(R.id.txtViewDawin);
        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewTeam.setTypeface(tfLight);
        txtViewDawin.setTypeface(tfLight);
        txtTristant.setTypeface(tfLight);
        txtManon.setTypeface(tfLight);
        txtYanick.setTypeface(tfLight);
        txtLucas.setTypeface(tfLight);

        // Retour accueil
        home = (ImageView) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TeamActivity.this, MainActivity.class);
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
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewTeam.getText().toString(),tts);
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
    public void onInit(int status) {}

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TeamActivity.this, MainActivity.class);
        intent.putExtra("retour", true);
        startActivity(intent);
    }
}