package com.abcgeometrie.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.abcgeometrie.R;

public class AdminQuestionsActivity extends Activity {

    private Button q1, q2, q3, deco, adm;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_questions);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        q1 = (Button) findViewById(R.id.btn1);
        q2 = (Button) findViewById(R.id.btn2);
        q3 = (Button) findViewById(R.id.btn3);
        deco = (Button) findViewById(R.id.btnTv);
        adm = (Button) findViewById(R.id.btnDeco);

        tv = (TextView) findViewById(R.id.tvAdmin);

        q1.setTypeface(tfMedium);
        q2.setTypeface(tfMedium);
        q3.setTypeface(tfMedium);
        adm.setTypeface(tfMedium);
        deco.setTypeface(tfMedium);

        tv.setTypeface(tfLight);
    }
}
