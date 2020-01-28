package com.demo.voicetotext

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_speachtotext.setOnClickListener {
                startActivity(Intent(this,SpeechToTextActivity::class.java))
        }

        button_qua_ans.setOnClickListener {
                startActivity(Intent(this,QuationAnsActivity::class.java))
        }

        button_voice_recognization.setOnClickListener {
                startActivity(Intent(this,VoiceRecgnizationActivity::class.java))
        }


    }
}