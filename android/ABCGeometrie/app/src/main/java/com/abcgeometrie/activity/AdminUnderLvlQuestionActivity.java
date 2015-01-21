package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.abcgeometrie.R;

public class AdminUnderLvlQuestionActivity extends Activity {

    private Button btnAddQuestion, btnLogOut;
    private TextView txtViewAdmin, txtViewQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_under_level_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtViewAdmin = (TextView) findViewById(R.id.txtViewAdmin);
        btnAddQuestion = (Button) findViewById(R.id.btnAddQuestion);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        txtViewQuest = (TextView) findViewById(R.id.txtViewQuest);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewAdmin.setTypeface(tfMedium);
        btnAddQuestion.setTypeface(tfMedium);
        btnLogOut.setTypeface(tfMedium);
        txtViewQuest.setTypeface(tfLight);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminUnderLvlQuestionActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminUnderLvlQuestionActivity.this, CreateQuestionActivity.class);
                startActivity(i);
            }
        });
    }
}
