package com.example.playlistmaker.sharing.data.repository.impl


import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.data.repository.ExternalNavigator


class ExternalNavigatorImpl() : ExternalNavigator {
    override fun openTerms() {

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(Creator.getApplication().getString(R.string.YP_user_cond_link))
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Creator.getApplication().startActivity(intent)
    }

    override fun shareApp() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, Creator.getApplication().getString(R.string.YP_link_array))
            type = "text/plain"
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Creator.getApplication().startActivity(intent)
    }

    override fun writeSupport() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(Creator.getApplication().getString(R.string.YP_student_email))
            )
            putExtra(
                Intent.EXTRA_SUBJECT,
                Creator.getApplication().getString(R.string.YP_support_subject)
            )
            putExtra(
                Intent.EXTRA_TEXT,
                Creator.getApplication().getString(R.string.YP_support_message)
            )
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Creator.getApplication().startActivity(intent)
    }

}