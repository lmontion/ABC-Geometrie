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
import android.widget.TextView;

import com.abcgeometrie.R;

public class AdminQuestionsActivity extends Activity {

    private Button q1, q2, q3, deco;
    private TextView tv, adm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_questions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        q1 = (Button) findViewById(R.id.btn1);
        q2 = (Button) findViewById(R.id.btn2);
        q3 = (Button) findViewById(R.id.btn3);
        adm = (TextView) findViewById(R.id.txtViewAdmin);
        deco = (Button) findViewById(R.id.btnDeco);

        tv = (TextView) findViewById(R.id.tvAdmin);

        q1.setTypeface(tfMedium);
        q2.setTypeface(tfMedium);
        q3.setTypeface(tfMedium);
        adm.setTypeface(tfMedium);
        deco.setTypeface(tfMedium);

        tv.setTypeface(tfLight);

        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminQuestionsActivity.this, AdminUnderLvlQuestionActivity.class);
                startActivity(i);
            }
        });

        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminQuestionsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
