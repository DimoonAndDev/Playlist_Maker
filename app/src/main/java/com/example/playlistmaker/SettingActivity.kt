package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val ShareButton = findViewById<Button>(R.id.ShareButton)
        val SupportButton = findViewById<Button>(R.id.SupportButton)
        val UserCondButton = findViewById<Button>(R.id.UserCondButton)


        ShareButton.setOnClickListener {
            val shareIntent = Intent()
            val message = getString(R.string.YP_link_array)
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            shareIntent.type = "text/plain"
            startActivity(shareIntent)
        }

        SupportButton.setOnClickListener {
            val message = getString(R.string.YP_support_message)
            val supportIntent = Intent(Intent.ACTION_SENDTO)
            supportIntent.data = Uri.parse("mailto:")
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.YP_student_email)))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.YP_support_subject))
            supportIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(supportIntent)
        }
        UserCondButton.setOnClickListener {
            val url = getString(R.string.YP_user_cond_link)
            val userCondIntent = Intent(Intent.ACTION_VIEW)
            userCondIntent.data = Uri.parse(url)
            startActivity(userCondIntent)
        }


    }
}