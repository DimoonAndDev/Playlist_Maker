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
            this.finish()
        }



        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> switchDarkMode.isChecked = true
            Configuration.UI_MODE_NIGHT_NO -> switchDarkMode.isChecked = false
        }
        switchDarkMode.setOnClickListener {
            if (switchDarkMode.isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        shareButton.setOnClickListener {
            Intent().apply {
                val message = getString(R.string.YP_link_array)
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
                startActivity(this@apply)
            }
        }

        supportButton.setOnClickListener {
            val message = getString(R.string.YP_support_message)
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(getString(R.string.YP_student_email))
                )
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.YP_support_subject))
                putExtra(Intent.EXTRA_TEXT, message)
                startActivity(this@apply)
            }
        }
        userCondButton.setOnClickListener {
            val url = getString(R.string.YP_user_cond_link)
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                startActivity(this@apply)
            }
        }


    }
}