package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import com.abcgeometrie.R;
import com.abcgeometrie.metier.Contrat;
import com.abcgeometrie.metier.DbAdapter;
import com.abcgeometrie.metier.Question;

public class QuestionActivity extends Activity implements TextToSpeech.OnInitListener{

    private ImageView speak;
    private TextToSpeech tts;
    private TextView txtViewQuestion;
    private Button btnLang;
    private ImageButton rep, img1, img2, img3;
    private DialogLang dl;
    private String lang = "";
    private String currentTheme;
    private int currentLvl, currentNbPointsContrat;
    private Contrat con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_haut, R.anim.slide_bas);

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(QuestionActivity.this);

        currentTheme = (String) getIntent().getExtras().get("theme");
        currentLvl = (int) getIntent().getExtras().get("lvl");
        currentNbPointsContrat = (int) getIntent().getExtras().get("nbPoints");

        // Récupération bouton et evenement

        rep = (ImageButton) findViewById(R.id.rep1);
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuestionActivity.this, EndGameActivity.class);
                startActivity(i);
            }
        });


        img1 = (ImageButton) findViewById(R.id.rep2);
        img2 = (ImageButton) findViewById(R.id.rep3);
        img3 = (ImageButton) findViewById(R.id.rep4);

        // Application de la police
        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewQuestion.setTypeface(tfLight);


        // Récupération langue en cours + event speaker
        tts = new TextToSpeech(this, this);
        speak = (ImageView) findViewById(R.id.btnTTS);
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewQuestion.getText().toString(),tts);
            }
        });
        //txtViewQuestion.setText("lvl : "+currentLvl+" theme = "+currentTheme+" point contrat -> "+currentNbPointsContrat);

        // Drapeaux et event changement langue
        btnLang = (Button) findViewById(R.id.btnLang);
        btnLang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dl.onCreateDialog();
            }
        });

        //lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();

        DbAdapter db = new DbAdapter(this);
        db.open();

        con = db.getcontratByNiveauAndTheme(currentLvl, currentNbPointsContrat, currentTheme);
        Question question = con.chooseAQuestion();

        txtViewQuestion.setText(question.getLibelleFR());
/*
        String foo = "This,that,other";
        String[] split = foo.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            sb.append(split[i]);
            if (i != split.length - 1) {
                sb.append(" ");
            }
        }
        String joined = sb.toString();*/

        String test = question.getUrlImgSol();
        String temp = test.split("\\.")[0];

        //String bidon = "R.drawable."+temp;

        //rep.setImageResource(Integer.parseInt(bidon));

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aller a l'accueil");
        DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        builder.setPositiveButton("OK", ok);
        builder.setNegativeButton("Annuler", null);
        builder.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.slide_out_return);
    }

    @Override
    public void onInit(int status) {}
}
