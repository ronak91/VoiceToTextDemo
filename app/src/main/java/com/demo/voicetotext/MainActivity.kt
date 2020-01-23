package com.demo.voicetotext

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val RequestPermissionCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //checkPermission()
        //onCreate
        if (CheckingPermissionIsEnabledOrNot()) { //Toast.makeText(ActivityLogin.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        } else { //Calling method to enable permission.
            RequestMultiplePermission()
        }

        val editText: EditText = findViewById(R.id.editText)

        val mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)


        val mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mSpeechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        mSpeechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )


        mSpeechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onEndOfSpeech() {}
            override fun onError(i: Int) {}
            override fun onResults(bundle: Bundle) { //getting all the matches
                val matches = bundle
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                //displaying the first match
                if (matches != null) editText.setText(matches[0])
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })

        button.setOnTouchListener(OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    mSpeechRecognizer.stopListening()
                    editText.hint = "You will see input here"
                }
                MotionEvent.ACTION_DOWN -> {
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent)
                    editText.setText("")
                    editText.hint = "Listening..."
                }
            }
            false
        })
    }


    fun CheckingPermissionIsEnabledOrNot(): Boolean {
        val FirstPermissionResult: Int = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO)
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED
    }

    private fun RequestMultiplePermission() { // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.RECORD_AUDIO
            ), RequestPermissionCode
        )
    }

    private fun checkPermission() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
                finish()
            }
    }
}
