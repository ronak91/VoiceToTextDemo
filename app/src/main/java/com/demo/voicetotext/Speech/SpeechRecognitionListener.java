package com.demo.voicetotext.Speech;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Ronak Upadhyay on 28/01/20.
 */
public class SpeechRecognitionListener implements RecognitionListener
{

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech()
    {
        Log.e("SPEECH", "onBeginingOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer)
    {

    }

    @Override
    public void onEndOfSpeech()
    {
        Log.e("SPEECH", "onReadyForSpeech"); //$NON-NLS-1$
    }

    @Override
    public void onError(int error)
    {
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        Log.e("SPEECH", "error = " + error);
    }

    @Override
    public void onResults(Bundle results) {

        //Log.d(TAG, "onResults"); //$NON-NLS-1$
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        onSpeechResults(matches);
        // matches are the return values of speech recognition engine
        // Use these values for whatever you wish to do

    }

    @Override
    public void onPartialResults(Bundle partialResults) {

        ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (String match : matches) {
            match = match.toLowerCase();
            Log.e("SPEECH", "onPartialResults : " + match);
        }

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }


    public String onSpeechResults(ArrayList<String> matches) {
        String result = "";
        String  match  = "";

        for(int i = 0;i<matches.size();i++){
            match = matches.get(i).toLowerCase();
        }

//        for (match : matches) {
//
//            match = match.toLowerCase();
//            Log.d("SPEECH", "Got speech: " + match);
//
//            if (match.contains("go")) {
//                //Do Something
//                mSpeechRecognizer.stopListening();
//            }
//            if (match.contains("stop")) {
//                //Do Something
//                mSpeechRecognizer.stopListening();
//            }
//
//        }

        return match;
    }


}


