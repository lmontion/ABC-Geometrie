package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
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
    private Button btnEn, btnFr, btnEs;
    private Locale myLocale;
    private String lang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        tts = new TextToSpeech(this,this);

        txtViewLvl3 = (TextView) findViewById(R.id.txtViewLvl3);
        txtViewLvl2 = (TextView) findViewById(R.id.txtViewLvl2);
        txtViewLvl1 = (TextView) findViewById(R.id.txtViewLvl1);
        txtViewSelectLvl = (TextView) findViewById(R.id.txtViewSelectLvl);

        home = (ImageView) findViewById(R.id.btnHome);
        speak = (ImageView) findViewById(R.id.btnTTS);

        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewSelectLvl.setTypeface(tfLight);
        txtViewLvl1.setTypeface(tfMedium);
        txtViewLvl2.setTypeface(tfMedium);
        txtViewLvl3.setTypeface(tfMedium);

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
                startActivity(i);
            }
        });

        txtViewLvl3.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseLevelActivity.this, ChooseDefiActivity.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseLevelActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(0, R.anim.fade_out);
            }
        });

        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewSelectLvl.getText().toString(),tts);
            }
        });

        btnEn = (Button) findViewById(R.id.btnEn);
        btnFr = (Button) findViewById(R.id.btnLang);
        btnEs = (Button) findViewById(R.id.btnEs);

        if(lang.equals("fr")){
            btnEn.setVisibility(View.GONE);
            btnEs.setVisibility(View.GONE);
        }
        if(lang.equals("es")){
            btnEn.setVisibility(View.GONE);
            btnFr.setVisibility(View.GONE);
        }
        if(lang.equals("en")){
            btnEs.setVisibility(View.GONE);
            btnFr.setVisibility(View.GONE);
        }

        btnEn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lang == "en"){
                    btnEs.setVisibility(View.VISIBLE);
                    btnFr.setVisibility(View.VISIBLE);
                }else{
                    lang = "en";
                    changeLang(lang);
                    startActivity(new Intent(getBaseContext(), ChooseLevelActivity.class));
                }
            }
        });

        btnFr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lang == "fr"){
                    btnEs.setVisibility(View.VISIBLE);
                    btnEn.setVisibility(View.VISIBLE);
                }else{
                    lang = "fr";
                    changeLang(lang);
                    startActivity(new Intent(getBaseContext(), ChooseLevelActivity.class));
                }
                //onCreateDialog();
            }
        });

        btnEs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lang == "es"){
                    btnEn.setVisibility(View.VISIBLE);
                    btnFr.setVisibility(View.VISIBLE);
                }else{
                    lang = "es";
                    changeLang(lang);
                    startActivity(new Intent(getBaseContext(), ChooseLevelActivity.class));
                }
            }
        });

    }

    public void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layout = getLayoutInflater();
        View v = layout.inflate(R.layout.actvity_dialog, null);
        Button esp = (Button) v.findViewById(R.id.btnEs);
        esp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lang = "en";
                changeLang(lang);
                startActivity(new Intent(getBaseContext(), ChooseLevelActivity.class));
            }
        });

        builder.setView(v);
        builder.show();
    }

    public void changeLang(String lang){
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.slide_out_return);
    }

    @Override
    public void onInit(int status) {

    }
}
