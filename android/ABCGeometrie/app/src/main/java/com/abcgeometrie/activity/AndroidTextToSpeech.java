package com.abcgeometrie.activity;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;

/**
 * Created by lucas on 22/01/15.
 */
public class AndroidTextToSpeech implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private String lang, text;

    public AndroidTextToSpeech(String lang, String text, TextToSpeech tts) {
        this.lang = lang;
        this.text = text;
        this.tts = tts;
        langue();
    }

    @Override
    public void onInit(int status) {}

    private void langue(){
        int result = 0;

        if (lang == "fr"){
            result = tts.setLanguage(Locale.FRENCH);
        }
        if (lang == "en"){
            result = tts.setLanguage(Locale.US);
        }
        if (lang == "es"){
            Locale locSpanish = new Locale("spa", "ESP");
            result = tts.setLanguage(locSpanish);
        }
        // tts.setPitch(5); // set pitch level
        // tts.setSpeechRate(2); // set speech speed rate

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TTS", "Language is not supported");
        } else {
            //btnSpeak.setEnabled(true);
            speakOut();
        }
    }

    private void speakOut() {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
