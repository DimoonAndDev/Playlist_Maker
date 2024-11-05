package com.example.playlistmaker.sharing.data.repository.impl


import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.data.repository.ExternalNavigator


class ExternalNavigatorImpl(private val contextApp: Context) : ExternalNavigator {

    override fun openTerms() {

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(contextApp.getString(R.string.YP_user_cond_link))
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        contextApp.startActivity(intent)
    }

    override fun shareApp() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, contextApp.getString(R.string.YP_link_array))
            type = "text/plain"
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        contextApp.startActivity(intent)
    }

    override fun writeSupport() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(contextApp.getString(R.string.YP_student_email))
            )
            putExtra(
                Intent.EXTRA_SUBJECT,
                contextApp.getString(R.string.YP_support_subject)
            )
            putExtra(
                Intent.EXTRA_TEXT,
                contextApp.getString(R.string.YP_support_message)
            )
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        contextApp.startActivity(intent)
    }

}