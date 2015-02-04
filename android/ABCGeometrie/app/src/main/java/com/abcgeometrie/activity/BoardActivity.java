package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.abcgeometrie.R;
import com.abcgeometrie.metier.Contrat;
import com.abcgeometrie.metier.DbAdapter;
import com.abcgeometrie.metier.Gagnant;

import java.util.ArrayList;

/**
 * Created by Yanick on 22/01/2015.
 */
public class BoardActivity extends ListActivity implements TextToSpeech.OnInitListener{

    private ImageView speak, home;
    private TextToSpeech tts;
    private TextView txtViewBoard;
    private DialogLang dl;
    private Button btnLang;
    private String lang = "";
    private Gagnant[] lstGagnants;
    protected DbAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // Plein écran
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animation
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        // Application de la police
        txtViewBoard = (TextView) findViewById(R.id.txtViewBoard);
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/orbitron-light.otf");
        Typeface tfMedium = Typeface.createFromAsset(getAssets(),"fonts/orbitron-medium.otf");
        txtViewBoard.setTypeface(tfLight);

        // Retour accueil
        home = (ImageView) findViewById(R.id.btnHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Génération de la liste des gagnants
        db = new DbAdapter(this);
        db.open();
        Contrat currentContrat = (Contrat) getIntent().getExtras().get("contrat");
        lstGagnants = db.getGagnantsByIdContrat(currentContrat.getId());
        ColorArrayAdapter dataAdapter = new ColorArrayAdapter(this, R.layout.textview_gagnants, lstGagnants);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        setListAdapter(dataAdapter);


        // Boite de dialogue changement langue et affichage drapeaux
        dl = new DialogLang(BoardActivity.this, currentContrat);

        // Récupération langue en cours + event speaker
        tts = new TextToSpeech(this,this);
        speak = (ImageView) findViewById(R.id.btnTTS);
        lang = getBaseContext().getResources().getConfiguration().locale.getLanguage();
        speak.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String read = "";
                if (lstGagnants.length >= 1) read = lstGagnants[0].read(lstGagnants, lang);
                AndroidTextToSpeech textToSpeech = new AndroidTextToSpeech(lang,txtViewBoard.getText().toString() + read,tts);
            }
        });

        // Drapeaux et event changement langue
        btnLang = (Button) findViewById(R.id.btnLang);
        btnLang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dl.onCreateDialog();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onNewIntent(getIntent());
        overridePendingTransition(0,R.anim.slide_out_return);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aller a l'accueil");
        DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
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
    public void onInit(int status) {}
}
