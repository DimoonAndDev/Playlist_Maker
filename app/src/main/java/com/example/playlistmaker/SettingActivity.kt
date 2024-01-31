package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val setting_backButton = findViewById<Button>(R.id.setting_backButton)
        setting_backButton.setOnClickListener{
            val intent = Intent(this@SettingActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }
}