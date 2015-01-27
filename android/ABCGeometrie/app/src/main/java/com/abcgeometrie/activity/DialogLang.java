package com.abcgeometrie.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.abcgeometrie.R;

import java.util.Locale;

/**
 * Created by Yanick on 27/01/2015.
 */
public class DialogLang {

    private Locale myLocale;
    private String lang;
    private Activity act = null;

    public DialogLang(Activity a) {
        this.act = a;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        LayoutInflater layout = act.getLayoutInflater();
        View v = layout.inflate(R.layout.actvity_dialog, null);
        Button esp = (Button) v.findViewById(R.id.btnEs);
        esp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lang = "es";
                changeLang(lang);
                act.startActivity(new Intent(act.getBaseContext(), act.getClass()));
            }
        });
        Button fr = (Button) v.findViewById(R.id.btnLang);
        fr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lang = "fr";
                changeLang(lang);
                act.startActivity(new Intent(act.getBaseContext(), act.getClass()));
            }
        });
        Button eng = (Button) v.findViewById(R.id.btnEn);
        eng.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lang = "en";
                changeLang(lang);
                act.startActivity(new Intent(act.getBaseContext(), act.getClass()));
            }
        });
        builder.setView(v);
        builder.show();
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
