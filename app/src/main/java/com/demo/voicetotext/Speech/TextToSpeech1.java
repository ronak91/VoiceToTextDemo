package com.demo.voicetotext.Speech;


import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Ronak Upadhyay on 28/01/20.
 */
public class TextToSpeech1 {

    static TextToSpeech t1;
    private static TextToSpeech1 ourInstance = new TextToSpeech1();
    private Context appContext;
    private TextToSpeech1() { }
    public static Context get() {
        return getInstance().getContext();
    }
    public static synchronized TextToSpeech1 getInstance() {
        return ourInstance;
    }
    public void init(Context context) {
        if (appContext == null) {
            this.appContext = context;
        }
    }
    private Context getContext() {
        return appContext;
    }
    public void textToSpeach(String Speak) {
        t1 = new TextToSpeech(get(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        t1.speak(Speak, TextToSpeech.QUEUE_FLUSH, null);
    }
}