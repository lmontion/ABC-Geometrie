package com.abcgeometrie.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.abcgeometrie.R;

public class QuestionActivity extends Activity {

    private TextView txtViewQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        overridePendingTransition(R.anim.slide_haut, R.anim.slide_bas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);

        txtViewQuestion.setTypeface(tfLight);
    }
}
