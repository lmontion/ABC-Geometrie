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
 * Created by Yanick on 22/01/2015.
 */
public class ChooseThemeActivity extends Activity implements TextToSpeech.OnInitListener{

    private TextView txtViewChooseTheme;
    private Button btnThemeColor, btnThemeForm, btnThemeColorAndForm;
    private ImageView speak, home;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_theme);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtViewChooseTheme = (TextView) findViewById(R.id.txtViewChooseTheme);
        btnThemeColor = (Button) findViewById(R.id.btnThemeColor);
        btnThemeForm = (Button) findViewById(R.id.btnThemeForm);
        btnThemeColorAndForm = (Button) findViewById(R.id.btnThemeColorAndForm);
        home = (ImageView) findViewById(R.id.btnHome);
        tts = new TextToSpeech(this,this);
        speak = (ImageView) findViewById(R.id.btnTTS);

        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewChooseTheme.setTypeface(tfLight);
        btnThemeColor.setTypeface(tfMedium);
        btnThemeForm.setTypeface(tfMedium);
        btnThemeColorAndForm.setTypeface(tfMedium);

        btnThemeColor.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, ChooseDefiActivity.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseThemeActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(0,R.anim.fade_out);
            }
        });

        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech("FR",txtViewChooseTheme.getText().toString(),tts);
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
    public void onInit(int status) {

    }
}
