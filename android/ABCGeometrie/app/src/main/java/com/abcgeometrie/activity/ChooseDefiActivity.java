package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_defi);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tts = new TextToSpeech(this,this);

        tv = (TextView) findViewById(R.id.textChooseDefi);

        btnLang = (Button) findViewById(R.id.btnLang);
        btn10 = (Button) findViewById(R.id.btn10);
        btn20 = (Button) findViewById(R.id.btn20);
        btn40 = (Button) findViewById(R.id.btn40);
        home = (ImageView) findViewById(R.id.btnHome);
        speak = (ImageView) findViewById(R.id.btnTTS);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        tv.setTypeface(tfLight);
        btn10.setTypeface(tfMedium);
        btn20.setTypeface(tfMedium);
        btn40.setTypeface(tfMedium);

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, QuestionActivity.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech("FR",tv.getText().toString(),tts);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.slide_haut_return);
    }

    @Override
    public void onInit(int status) {

    }
}
