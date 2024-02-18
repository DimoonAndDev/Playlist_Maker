package com.example.playlistmaker

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val backArrowImage = findViewById<ImageView>(R.id.SettingsBackArrowImage)
        val switchDarkMode = findViewById<SwitchCompat>(R.id.SettingSwitchDarkTheme)
        val shareButton = findViewById<Button>(R.id.ShareButton)
        val supportButton = findViewById<Button>(R.id.SupportButton)
        val userCondButton = findViewById<Button>(R.id.UserCondButton)

        backArrowImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
            Configuration.UI_MODE_NIGHT_YES-> switchDarkMode.isChecked = true
            Configuration.UI_MODE_NIGHT_NO-> switchDarkMode.isChecked = false
        }
        switchDarkMode.setOnClickListener {
            if (switchDarkMode.isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        shareButton.setOnClickListener {
            val shareIntent = Intent()
            val message = getString(R.string.YP_link_array)
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            shareIntent.type = "text/plain"
            startActivity(shareIntent)
        }

        supportButton.setOnClickListener {
            val message = getString(R.string.YP_support_message)
            val supportIntent = Intent(Intent.ACTION_SENDTO)
            supportIntent.data = Uri.parse("mailto:")
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.YP_student_email)))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.YP_support_subject))
            supportIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(supportIntent)
        }
        userCondButton.setOnClickListener {
            val url = getString(R.string.YP_user_cond_link)
            val userCondIntent = Intent(Intent.ACTION_VIEW)
            userCondIntent.data = Uri.parse(url)
            startActivity(userCondIntent)
        }


    }
}