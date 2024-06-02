package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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
        switchDarkMode.isChecked = (applicationContext as App).darkTheme
        switchDarkMode.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).switchTheme(checked)
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

