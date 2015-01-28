package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
    private Contrat con = null;
    private Contrat conTemp = null;
    private int idContrat;
    private Question question = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_haut, R.anim.slide_bas);

        // Récupération bouton et evenement
        rep = (ImageButton) findViewById(R.id.rep1);
        img1 = (ImageButton) findViewById(R.id.rep2);
        img2 = (ImageButton) findViewById(R.id.rep3);
        img3 = (ImageButton) findViewById(R.id.rep4);
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuestionActivity.this, EndGameActivity.class);
                startActivity(i);
            }
        });

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

        // Drapeaux et event changement langue
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        DbAdapter db = new DbAdapter(this);
        db.open();
        try{
            conTemp = (Contrat) getIntent().getExtras().get("contrat");
            con = conTemp;
        }catch (Exception e){
            Log.i("test","pas de contrat");
        }

        if (con == null){
            currentTheme = (String) getIntent().getExtras().get("theme");
            currentLvl = (int) getIntent().getExtras().get("lvl");
            currentNbPointsContrat = (int) getIntent().getExtras().get("nbPoints");

            con = db.getcontratByNiveauAndTheme(currentLvl, currentNbPointsContrat, currentTheme);
        }

        if (conTemp == null){
            question = con.chooseAQuestion();
        }else{
            question = (Question) getIntent().getExtras().get("question");
        }

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(QuestionActivity.this, con, question);
        btnLang = (Button) findViewById(R.id.btnLang);
        btnLang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dl.onCreateDialog();
            }
        });
        if(lang.equals("fr")){
            txtViewQuestion.setText(question.getLibelleFR());
        }
        if(lang.equals("es")){
            txtViewQuestion.setText(question.getLibelleES());
        }
        if(lang.equals("en")){
            txtViewQuestion.setText(question.getLibelleEN());
        }

        String tempUrlSol = question.getUrlImgSol();
        tempUrlSol = tempUrlSol.split("\\.")[0];

        String tempUrlImg1 = question.getUrlImg1();
        tempUrlImg1 = tempUrlImg1.split("\\.")[0];

        String tempUrlImg2 = question.getUrlImg2();
        tempUrlImg2 = tempUrlImg2.split("\\.")[0];

        String tempUrlImg3 = question.getUrlImg3();
        tempUrlImg3 = tempUrlImg3.split("\\.")[0];

        rep.setImageResource(getResources().getIdentifier("a"+tempUrlSol, "drawable", getPackageName()));
        img1.setImageResource(getResources().getIdentifier("a"+tempUrlImg1, "drawable", getPackageName()));
        img2.setImageResource(getResources().getIdentifier("a"+tempUrlImg2, "drawable", getPackageName()));
        img3.setImageResource(getResources().getIdentifier("a"+tempUrlImg3, "drawable", getPackageName()));

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
        DialogInterface.OnClickListener annuler = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
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
