package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcgeometrie.R;

/**
 * Created by Yanick on 22/01/2015.
 */
public class BoardActivity extends Activity implements TextToSpeech.OnInitListener{

    private ImageView speak;
    private TextToSpeech tts;
    private TextView txtViewBoard;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tts = new TextToSpeech(this,this);
        home = (Button) findViewById(R.id.btnHome);
        speak = (ImageView) findViewById(R.id.btnTTS);

        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewBoard = (TextView) findViewById(R.id.txtViewBoard);

        txtViewBoard.setTypeface(tfLight);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech("FR",txtViewBoard.getText().toString(),tts);
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
