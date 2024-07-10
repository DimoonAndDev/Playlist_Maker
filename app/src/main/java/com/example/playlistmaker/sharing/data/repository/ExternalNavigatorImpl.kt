package com.example.playlistmaker.sharing.data.repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.Creator
import com.example.playlistmaker.sharing.domain.models.EmailDetails

class ExternalNavigatorImpl(private val application: Application) : ExternalNavigator {
    override fun openTerms(termlink:String) {

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(termlink)
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Creator.getApplication().startActivity(intent)
    }

    override fun shareApp(shareLink:String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareLink)
            type = "text/plain"
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Creator.getApplication().startActivity(intent)
    }

    override fun writeSupport(emailDetails: EmailDetails){
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(emailDetails.address)
            )
            putExtra(Intent.EXTRA_SUBJECT, emailDetails.subject)
            putExtra(Intent.EXTRA_TEXT, emailDetails.text)
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Creator.getApplication().startActivity(intent)
    }

}