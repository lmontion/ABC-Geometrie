package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.abcgeometrie.R;
import com.abcgeometrie.metier.Contrat;
import com.abcgeometrie.metier.DbAdapter;
import com.abcgeometrie.metier.Jeu;
import com.abcgeometrie.metier.Question;
import java.util.ArrayList;
import java.util.Random;

public class QuestionActivity extends Activity implements TextToSpeech.OnInitListener{

    private ImageView speak;
    private TextToSpeech tts;
    private TextView txtViewQuestion, txtViewProgressBar;
    private Button btnLang;
    private ImageButton img0, img1, img2, img3;
    private ImageView croix1, croix2, croix3, croix4, nike1, nike2, nike3, nike4;
    private DialogLang dl;
    private String lang = "";
    private String currentTheme;
    private int currentLvl, currentNbPointsContrat;
    private Contrat con = null;
    private Contrat conTemp = null;
    private int idContrat;
    private Question question = null;
    private Jeu jeu = null;
    private boolean changementLang = false;
    private ProgressBar pb;
    private int i, k, position;
    private ImageButton mauvais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_haut, R.anim.slide_bas);

        img0 = (ImageButton) findViewById(R.id.rep1);
        img1 = (ImageButton) findViewById(R.id.rep2);
        img2 = (ImageButton) findViewById(R.id.rep3);
        img3 = (ImageButton) findViewById(R.id.rep4);
        pb = (ProgressBar) findViewById(R.id.progressBarVertical);
        croix1 = (ImageView) findViewById(R.id.croixRep1);
        croix2 = (ImageView) findViewById(R.id.croixRep2);
        croix3 = (ImageView) findViewById(R.id.croixRep3);
        croix4 = (ImageView) findViewById(R.id.croixRep4);
        nike1 = (ImageView) findViewById(R.id.nikeRep1);
        nike2 = (ImageView) findViewById(R.id.nikeRep2);
        nike3 = (ImageView) findViewById(R.id.nikeRep3);
        nike4 = (ImageView) findViewById(R.id.nikeRep4);

        final ImageView[] tabCroix = {croix1, croix2, croix3, croix4};
        final ImageView[] tabNike = {nike1, nike2, nike3, nike4};

        // Application de la police
        txtViewProgressBar = (TextView) findViewById(R.id.textViewProgressBar);
        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewQuestion.setTypeface(tfLight);
        txtViewProgressBar.setTypeface(tfLight);

        // Récupération langue en cours + event speaker
        tts = new TextToSpeech(this, this);
        speak = (ImageView) findViewById(R.id.btnTTS);
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();

        //speak.isPressed();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewQuestion.getText().toString(),tts);
            }
        });

        DbAdapter db = new DbAdapter(this);
        db.open();
        try {
            changementLang = (boolean) getIntent().getExtras().get("change");
        }catch (Exception e){

        }

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

            jeu = new Jeu(0,0);
            pb.setProgress(0);
            txtViewProgressBar.setText("O / "+con.getNbPoints());
        }else{
            jeu = (Jeu) getIntent().getExtras().get("jeu");

            pb.setProgress(jeu.getNbQuestionsReussis());
            txtViewProgressBar.setText(jeu.getNbQuestionsReussis() + " / " + con.getNbPoints());
        }

        if (changementLang){
            question = (Question) getIntent().getExtras().get("question");
        }else{
            question = con.chooseAQuestion();
        }
        pb.setMax(con.getNbPoints());

        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(QuestionActivity.this, con, question, jeu);
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

        // Retaillage du texte
        resize();

        final ImageButton[] tabImg = {img0, img1, img2, img3};

        Random r = new Random();
        final int indexRandom = r.nextInt(4);

        String tempUrlSol = question.getUrlImgSol();
        tempUrlSol = tempUrlSol.split("\\.")[0];

        String tempUrlImg1 = question.getUrlImg1();
        tempUrlImg1 = tempUrlImg1.split("\\.")[0];

        String tempUrlImg2 = question.getUrlImg2();
        tempUrlImg2 = tempUrlImg2.split("\\.")[0];

        String tempUrlImg3 = question.getUrlImg3();
        tempUrlImg3 = tempUrlImg3.split("\\.")[0];

        String[] urls = {tempUrlImg1, tempUrlImg2, tempUrlImg3};
        tabImg[indexRandom].setImageResource(getResources().getIdentifier("a" + tempUrlSol, "drawable", getPackageName()));

        final String[] reussi = getResources().getStringArray(R.array.arrayOK);
        final String[] perdu = getResources().getStringArray(R.array.arrayPASOK);
        final int indexReussi = r.nextInt(4);
        final int indexPerdu = r.nextInt(3);

        int j = 0;
        for (i = 0; i < tabImg.length; i++){
            if (i != indexRandom){
                tabImg[i].setImageResource(getResources().getIdentifier("a" + urls[j], "drawable", getPackageName()));
                tabImg[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (k = 0; k < tabImg.length; k++){
                            tabImg[k].setEnabled(false);
                            if (tabImg[k] == v){
                                position = k;
                            }
                        }
                        v.setBackgroundColor(Color.RED);
                        tabCroix[position].setVisibility(View.VISIBLE);
                        AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,perdu[indexPerdu],tts);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tabImg[indexRandom].setBackgroundResource(R.color.vertRep);
                            }
                        }, 1000);
                        jeu.setNbQuestionsNecessaires(jeu.getNbQuestionsNecessaires() + 1);
                        final Intent intent = new Intent(QuestionActivity.this, QuestionActivity.class);
                        intent.putExtra("contrat", con);
                        intent.putExtra("jeu", jeu);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                });
                j++;
            }
        }

        tabImg[indexRandom].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ImageButton img : tabImg){
                    img.setEnabled(false);
                }
                jeu.setNbQuestionsNecessaires(jeu.getNbQuestionsNecessaires() + 1);
                jeu.setNbQuestionsReussis(jeu.getNbQuestionsReussis() + 1);
                tabImg[indexRandom].setBackgroundResource(R.color.vertRep);
                tabNike[indexRandom].setVisibility(View.VISIBLE);
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,reussi[indexReussi],tts);
                ArrayList<Question> temp = con.getLstQuestions();
                temp.remove(question);
                con.setLstQuestions(temp);

                final Intent i;
                if (jeu.getNbQuestionsReussis() == con.getNbPoints()) {
                    i = new Intent(QuestionActivity.this, EndGameActivity.class);
                    jeu.stopChrono();
                } else {
                    i = new Intent(QuestionActivity.this, QuestionActivity.class);
                }
                i.putExtra("contrat", con);
                i.putExtra("jeu", jeu);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);
                        finish();
                    }
                }, 2000);
            }
        });

        View[] views = {btnLang, speak};
        for (View btn : views){
            btn.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setAlpha((float) 0.7);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        v.setAlpha((float) 1);
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        final Dialog dial = new Dialog(this, android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(220);
        dial.getWindow().setBackgroundDrawable(d);
        dial.setContentView(R.layout.actvity_dialogreturn);
        ImageView backHome = (ImageView) dial.findViewById(R.id.backHome);
        backHome.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity( new Intent(QuestionActivity.this, MainActivity.class));
                finish();
            }
        });
        ImageView backGame = (ImageView) dial.findViewById(R.id.backGame);
        backGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dial.cancel();
            }
        });
        RelativeLayout rl = (RelativeLayout) dial.findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dial.cancel();
            }
        });
        dial.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.slide_out_return);
    }

    @Override
    public void onInit(int status) {
        if(con.getNiveau().equals("1")) {
            AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang, txtViewQuestion.getText().toString(), tts);
        }
    }

    public void resize(){
        txtViewQuestion.post(new Runnable() {
            @Override
            public void run() {
                int nbLignes = txtViewQuestion.getLineCount();
                if(nbLignes >= 3){
                    txtViewQuestion.setTextSize(TypedValue.COMPLEX_UNIT_PX,(txtViewQuestion.getTextSize() - 2));
                    resize();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(tts != null)
            tts.shutdown();
        super.onDestroy();
    }
}
