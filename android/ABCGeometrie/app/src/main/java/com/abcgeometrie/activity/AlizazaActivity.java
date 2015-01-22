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
 * Created by Yanick on 22/01/2015.
 */
public class AlizazaActivity extends Activity{

    private TextView txtViewAlizaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alizaza);
        overridePendingTransition(R.anim.inverse_bas, R.anim.inverse_haut);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtViewAlizaza = (TextView) findViewById(R.id.txtViewAlizaza);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewAlizaza.setTypeface(tfLight);
    }
}
