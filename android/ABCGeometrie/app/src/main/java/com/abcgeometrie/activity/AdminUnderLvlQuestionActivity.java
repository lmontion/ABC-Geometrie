package com.abcgeometrie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.abcgeometrie.R;

/**
 * Created by Yanick on 20/01/2015.
 */
public class AdminUnderLvlQuestionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_under_level_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
