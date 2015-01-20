package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.abcgeometrie.R;


public class ChooseDefiActivity extends Activity {

    private Button btnLang, btn10, btn20, btn40;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_defi);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv = (TextView) findViewById(R.id.textChooseDefi);

        btnLang = (Button) findViewById(R.id.btnLang);
        btn10 = (Button) findViewById(R.id.btn10);
        btn20 = (Button) findViewById(R.id.btn20);
        btn40 = (Button) findViewById(R.id.btn40);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        tv.setTypeface(tfLight);
        btn10.setTypeface(tfMedium);
        btn20.setTypeface(tfMedium);
        btn40.setTypeface(tfMedium);

        btnLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseDefiActivity.this, ChooseLevelActivity.class);
                startActivity(i);
            }
        });
    }

}
