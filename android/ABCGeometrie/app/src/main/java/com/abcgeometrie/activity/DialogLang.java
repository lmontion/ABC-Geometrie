package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.abcgeometrie.R;
import com.abcgeometrie.metier.Contrat;
import com.abcgeometrie.metier.Jeu;
import com.abcgeometrie.metier.Question;
import java.util.Locale;

/**
 * Created by Yanick on 27/01/2015.
 */
public class DialogLang {

    private Locale myLocale;
    private String lang;
    private Activity act = null;
    private Contrat con = null;
    private Question  question = null;
    private Jeu jeu = null;
    private String theme;
    private int level;

    public DialogLang(Activity a, Contrat c, Question q, Jeu j) {
        this.act = a;
        this.con = c;
        this.question = q;
        this.jeu = j;
        hiddenFlags();
    }

    public DialogLang(Activity a, Contrat contrat){
        this.act = a;
        this.con = contrat;
        hiddenFlags();
    }

    public DialogLang(Activity a, Contrat contrat, Jeu jeu) {
        this.act = a;
        this.con = contrat;
        this.jeu = jeu;
        hiddenFlags();
    }

    public DialogLang(Activity a) {
        this.act = a;
        hiddenFlags();
    }

    public DialogLang(Activity a, String theme, int level){
        this.act = a;
        this.theme = theme;
        this.level = level;
        hiddenFlags();
    }

    public void hiddenFlags (){
        lang = act.getBaseContext().getResources().getConfiguration().locale.getLanguage();
        if(lang.equals("fr")){
            act.findViewById(R.id.btnLang).setBackgroundResource(R.drawable.france);
        }
        if(lang.equals("es")){
            act.findViewById(R.id.btnLang).setBackgroundResource(R.drawable.espagne);
        }
        if(lang.equals("en")){
            act.findViewById(R.id.btnLang).setBackgroundResource(R.drawable.angleterre);
        }
    }

    public void onCreateDialog() {
        final Dialog dial = new Dialog(act, android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(220);
        dial.getWindow().setBackgroundDrawable(d);
        dial.setContentView(R.layout.actvity_dialog);
        Button esp = (Button) dial.findViewById(R.id.btnEs);
        RelativeLayout rl = (RelativeLayout) dial.findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dial.cancel();
            }
        });
        esp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lang = "es";
                changeLang(lang);
                Intent i = new Intent(act.getBaseContext(), act.getClass());
                if(act.getClass() == ChooseDefiActivity.class){
                    i.putExtra("theme", theme);
                    i.putExtra("lvl", level);
                }
                if (act.getClass() == QuestionActivity.class){
                    i.putExtra("contrat", con);
                    i.putExtra("question", question);
                    i.putExtra("change", true);
                    i.putExtra("jeu", jeu);
                }
                if(act.getClass() == BoardActivity.class){
                    i.putExtra("contrat", con);
                }
                if(act.getClass() == EndGameActivity.class ){
                    i.putExtra("jeu", jeu);
                    i.putExtra("contrat", con);
                }
                act.startActivity(i);
                act.finish();
            }
        });
        Button fr = (Button) dial.findViewById(R.id.btnLang);
        fr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lang = "fr";
                changeLang(lang);
                Intent i = new Intent(act.getBaseContext(), act.getClass());
                if(act.getClass() == ChooseDefiActivity.class){
                    i.putExtra("theme", theme);
                    i.putExtra("lvl", level);
                }
                if (act.getClass() == QuestionActivity.class){
                    i.putExtra("contrat", con);
                    i.putExtra("question", question);
                    i.putExtra("change", true);
                    i.putExtra("jeu", jeu);
                }
                if(act.getClass() == BoardActivity.class){
                    i.putExtra("contrat", con);
                }
                if(act.getClass() == EndGameActivity.class ){
                    i.putExtra("jeu", jeu);
                    i.putExtra("contrat", con);
                }
                act.startActivity(i);
                act.finish();
            }
        });
        Button eng = (Button) dial.findViewById(R.id.btnEn);
        eng.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lang = "en";
                changeLang(lang);
                Intent i = new Intent(act.getBaseContext(), act.getClass());
                if (act.getClass() == ChooseDefiActivity.class) {
                    i.putExtra("theme", theme);
                    i.putExtra("lvl", level);
                }
                if (act.getClass() == QuestionActivity.class) {
                    i.putExtra("contrat", con);
                    i.putExtra("question", question);
                    i.putExtra("change", true);
                    i.putExtra("jeu", jeu);
                }
                if (act.getClass() == BoardActivity.class) {
                    i.putExtra("contrat", con);
                }
                if (act.getClass() == EndGameActivity.class) {
                    i.putExtra("jeu", jeu);
                    i.putExtra("contrat", con);
                }
                act.startActivity(i);
                act.finish();
            }
        });
        dial.show();
    }

    public void changeLang(String lang){
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        act.getBaseContext().getResources().updateConfiguration(config, act.getBaseContext().getResources().getDisplayMetrics());
    }


}
