package com.abcgeometrie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abcgeometrie.R;

public class CreateQuestionActivity extends Activity {

    private Button btnCreateQuest, btnLogOut, btnOk, import1, import2, import3, import4;
    private TextView txtViewAdmin, txtViewLabelCreateQuest;
    private EditText intituleQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtViewAdmin = (TextView) findViewById(R.id.txtViewAdmin);
        txtViewLabelCreateQuest = (TextView) findViewById(R.id.txtViewLabelCreateQuest);
        btnCreateQuest = (Button) findViewById(R.id.btnCreateQuest);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnOk = (Button) findViewById(R.id.btnOk);
        import1 = (Button) findViewById(R.id.import1);
        import2 = (Button) findViewById(R.id.import2);
        import3 = (Button) findViewById(R.id.import3);
        import4 = (Button) findViewById(R.id.import4);
        intituleQuest = (EditText) findViewById(R.id.intituleQuest);

        Typeface tfLight = Typeface.createFromAsset(getAssets(),"fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");

        txtViewAdmin.setTypeface(tfMedium);
        btnOk.setTypeface(tfMedium);
        btnCreateQuest.setTypeface(tfMedium);
        txtViewLabelCreateQuest.setTypeface(tfLight);
        import1.setTypeface(tfMedium);
        import2.setTypeface(tfMedium);
        import3.setTypeface(tfMedium);
        import4.setTypeface(tfMedium);
        intituleQuest.setTypeface(tfLight);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateQuestionActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
