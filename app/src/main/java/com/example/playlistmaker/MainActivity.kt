package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.searchButton)
        val mediaButton = findViewById<Button>(R.id.mediaButton)
        val settingButton = findViewById<Button>(R.id.settingsButton)

        searchButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
        val mediaButtonClickListener = View.OnClickListener {
            val intent = Intent(this@MainActivity, MediaActivity::class.java)
            startActivity(intent)
        }
        mediaButton.setOnClickListener(mediaButtonClickListener)

        settingButton.setOnClickListener{
            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}