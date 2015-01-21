package com.abcgeometrie.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.abcgeometrie.R;

/**
 * Created by Yanick on 21/01/2015.
 */
public class TeamActivity extends Activity {

    private TextView txtViewTeam, txtTristant, txtManon, txtYanick, txtLucas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtViewTeam = (TextView) findViewById(R.id.txtViewTeam);
        txtTristant = (TextView) findViewById(R.id.txtTristant);
        txtManon = (TextView) findViewById(R.id.txtManon);
        txtYanick = (TextView) findViewById(R.id.txtYanick);
        txtLucas = (TextView) findViewById(R.id.txtLucas);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewTeam.setTypeface(tfLight);
        txtTristant.setTypeface(tfLight);
        txtManon.setTypeface(tfLight);
        txtYanick.setTypeface(tfLight);
        txtLucas.setTypeface(tfLight);
    }
}