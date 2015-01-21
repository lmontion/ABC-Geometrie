package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.abcgeometrie.R;

/**
 * Created by Dubusier on 20/01/2015.
 */
public class HomeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final RelativeLayout relativeLayout;
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutHome);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ChooseDefiActivity.class);
                startActivity(i);
            }
        });
    }
}
