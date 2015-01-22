package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abcgeometrie.R;


public class MainActivity extends Activity {

    private ImageButton btnTeam, btnAlizaza;
    private RelativeLayout relativeLayout;
    private TextView abc, dela, geo1, geo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        abc = (TextView) findViewById(R.id.textViewAbc);
        dela = (TextView) findViewById(R.id.textViewDeLa);
        geo1 = (TextView) findViewById(R.id.textViewGeometrie01);
        geo2 = (TextView) findViewById(R.id.textViewGeometrie02);
        btnAlizaza = (ImageButton) findViewById(R.id.btnAlizaza);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutHome);
        btnTeam = (ImageButton) findViewById(R.id.btnIUT);

        abc.setTypeface(tfLight);
        dela.setTypeface(tfLight);
        geo1.setTypeface(tfLight);
        geo2.setTypeface(tfLight);


        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TeamActivity.class);
                startActivity(i);
            }
        });

        btnAlizaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AlizazaActivity.class);
                startActivity(i);
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChooseLevelActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.fade_out);
    }
}
