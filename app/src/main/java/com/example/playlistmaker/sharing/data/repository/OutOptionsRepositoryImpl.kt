package com.example.playlistmaker.sharing.data.repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.R
import com.example.playlistmaker.settings.domain.repository.OutOptionsRepository

class OutOptionsRepositoryImpl(private val application: Application) : OutOptionsRepository {
    override fun openTerms(): Intent {
        val url = application.getString(R.string.YP_user_cond_link)
        return Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
    }

    override fun shareApp(): Intent {
        return Intent().apply {
            val message = application.getString(R.string.YP_link_array)
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
    }

    override fun writeSupport(): Intent {
        val message = application.getString(R.string.YP_support_message)
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(application.getString(R.string.YP_student_email))
            )
            putExtra(Intent.EXTRA_SUBJECT, application.getString(R.string.YP_support_subject))
            putExtra(Intent.EXTRA_TEXT, message)
        }
    }

}