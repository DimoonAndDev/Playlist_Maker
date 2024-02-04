package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val setting_backButton = findViewById<ImageView>(R.id.SettingBackArrowImage)
        setting_backButton.setOnClickListener{
            val intent = Intent(this@SettingActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }
}