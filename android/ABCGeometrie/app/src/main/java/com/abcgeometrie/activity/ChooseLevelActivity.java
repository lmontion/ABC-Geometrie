package com.abcgeometrie.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.abcgeometrie.R;

public class ChooseLevelActivity extends Activity {

    private Button btnLang;
    private TextView txtViewLvl2, txtViewLvl3, txtViewLvl1, txtViewSelectLvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtViewLvl3 = (TextView) findViewById(R.id.txtViewLvl3);
        txtViewLvl2 = (TextView) findViewById(R.id.txtViewLvl2);
        txtViewLvl1 = (TextView) findViewById(R.id.txtViewLvl1);
        txtViewSelectLvl = (TextView) findViewById(R.id.txtViewSelectLvl);
        btnLang = (Button) findViewById(R.id.btnLang);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewSelectLvl.setTypeface(tfLight);
        txtViewLvl1.setTypeface(tfMedium);
        txtViewLvl2.setTypeface(tfMedium);
        txtViewLvl3.setTypeface(tfMedium);
    }
}
