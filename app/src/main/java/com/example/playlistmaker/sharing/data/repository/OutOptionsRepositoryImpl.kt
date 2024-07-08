package com.example.playlistmaker.sharing.data.repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.R
import com.example.playlistmaker.settings.domain.models.IntentModel
import com.example.playlistmaker.settings.domain.repository.OutOptionsRepository

class OutOptionsRepositoryImpl(private val application: Application) : OutOptionsRepository {
    override fun openTerms(): IntentModel {
        val url = application.getString(R.string.YP_user_cond_link)
        return IntentModel(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }

    override fun shareApp(): IntentModel {
        return IntentModel(Intent().apply {
            val message = application.getString(R.string.YP_link_array)
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        })
    }

    override fun writeSupport(): IntentModel {
        val message = application.getString(R.string.YP_support_message)
        return IntentModel(Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(application.getString(R.string.YP_student_email))
            )
            putExtra(Intent.EXTRA_SUBJECT, application.getString(R.string.YP_support_subject))
            putExtra(Intent.EXTRA_TEXT, message)
        })
    }

}