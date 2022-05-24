package com.sumon.roomdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {

    lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startButton = findViewById(R.id.start_btn)

        startButton.setOnClickListener {

            val intent = Intent(this, MainActivity :: class.java)
            startActivity(intent)
        }
    }
}