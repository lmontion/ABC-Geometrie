package com.abcgeometrie.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.abcgeometrie.R;

/**
 * Created by Yanick on 21/01/2015.
 */
public class TeamActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewTeam, txtTristant, txtManon, txtYanick, txtLucas;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        overridePendingTransition(R.anim.inverse_bas, R.anim.inverse_haut);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtViewTeam = (TextView) findViewById(R.id.txtViewTeam);
        txtTristant = (TextView) findViewById(R.id.txtTristant);
        txtManon = (TextView) findViewById(R.id.txtManon);
        txtYanick = (TextView) findViewById(R.id.txtYanick);
        txtLucas = (TextView) findViewById(R.id.txtLucas);

        tts = new TextToSpeech(this,this);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewTeam.setTypeface(tfLight);
        txtTristant.setTypeface(tfLight);
        txtManon.setTypeface(tfLight);
        txtYanick.setTypeface(tfLight);
        txtLucas.setTypeface(tfLight);

        txtTristant.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech("FR","Tristant est gentil et aime les zizi ! Je sais qu'il a une sale gueule sur l'image mais viens jouer aux formes avec lui",tts);
            }
        });

        txtManon.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech("FR","Manon est la seule fille du groupe ! Elle a 2 ballons d'avance ! Viens jouer aux formes avec elle",tts);
            }
        });

        txtLucas.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech("FR","Lucas à 2000 dessins de bites a son actif ! Veux tu etre son prochain model ? Allez ! Viens jouer aux formes avec lui",tts);
            }
        });

        txtYanick.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech("FR","Yanick est une personne normal. Viens jouer au forme avec lui",tts);
            }
        });
    }

    @Override
    public void onInit(int status) {

    }
}