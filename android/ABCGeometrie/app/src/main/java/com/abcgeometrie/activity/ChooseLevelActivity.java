package com.abcgeometrie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.abcgeometrie.R;

public class ChooseLevelActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
