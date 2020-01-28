package com.demo.voicetotext


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quationans.*
import java.util.*


class QuationAnsActivity : BaseActivity() {

    private var textToSpeech: TextToSpeech? = null
    val RequestPermissionCode = 1

    val mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)


    val mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quationans)



        //checkPermission()
        //onCreate
        if (CheckingPermissionIsEnabledOrNot()) { //Toast.makeText(ActivityLogin.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        } else { //Calling method to enable permission.
            RequestMultiplePermission()
        }

        val editText1: EditText = findViewById(R.id.editText1)

        mSpeechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        mSpeechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )


        textToSpeech = TextToSpeech(applicationContext,
            OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val ttsLang = textToSpeech!!.setLanguage(Locale.US)
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                        || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED
                    ) {
                        Log.e("TTS", "The Language is not supported!")
                    } else {
                        Log.e("TTS", "Language Supported.")
                    }
                    Log.i("TTS", "Initialization success.")
                } else {
                    toast("TTS Initialization failed!")
                }


                textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onDone(utteranceId: String?) {
                        if (utteranceId.equals("myname")) {
                            Log.e("TTS", "Start Listning")

                          //  editText1.hint = "Listening..."
                          //  mSpeechRecognizer.startListening(mSpeechRecognizerIntent)

                          //  close()
                        }
                    }

                    override fun onError(utteranceId: String?) {
                        toast("error when speaking")
                    }

                    override fun onStart(utteranceId: String?) {
                        toast("speaking started")
                    }
                })

            })


        answer1.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    mSpeechRecognizer.stopListening()
                    editText1.hint = "You will see input here"
                }
                MotionEvent.ACTION_DOWN -> {
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent)
                    editText1.setText("")
                    editText1.hint = "Listening..."
                }
            }
            false
        })

        button_answer1.setOnClickListener {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent)
            editText1.setText("")
            editText1.hint = "Listening..."
        }

        button_done1.setOnClickListener {
            mSpeechRecognizer.stopListening()
            editText1.hint = "You will see input here"
        }

        button_speak1.setOnClickListener {
            val data = quation1.text.toString()
            SpeakText(data)
        }

        button_speak2.setOnClickListener {
            val data = quation2.text.toString()
            SpeakText(data)
        }


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
                if (matches != null) editText1.setText(matches[0])
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })

    }



    fun SpeakText(data: String) {
        Log.i("TTS", "button clicked: $data")

        val params = HashMap<String, String>()
        params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "myname"

        val speechStatus = textToSpeech!!.speak(data, TextToSpeech.QUEUE_FLUSH, params)

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!")
        } else {
            toast("Speech Success")
        }
    }

    fun close(){
        Handler().postDelayed({
            Log.e("text"," Stop listning")
            editText1.hint = "Done"
            mSpeechRecognizer.stopListening()
        }, 4000)
    }


    fun toast(text: String) {
        Toast.makeText(
            applicationContext,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
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

}