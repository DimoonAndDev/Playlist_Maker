package com.example.playlistmaker.sharing.domain.usecases

import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.sharing.data.repository.ExternalNavigator
import com.example.playlistmaker.sharing.domain.models.EmailDetails

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator) : SharingInteractor{
    override fun openTerms() {
        externalNavigator.openTerms(getTermsLink())
    }

    override fun shareApp() {
        externalNavigator.shareApp(getShareAppLink())
    }

    override fun writeSupport() {
        externalNavigator.writeSupport(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return Creator.getApplication().getString(R.string.YP_link_array)
    }

    private fun getSupportEmailData(): EmailDetails {
        return EmailDetails(
            Creator.getApplication().getString(R.string.YP_support_message),
            Creator.getApplication().getString(R.string.YP_support_subject),
            Creator.getApplication().getString(R.string.YP_student_email)
        )
    }

    private fun getTermsLink(): String {
        return Creator.getApplication().getString(R.string.YP_user_cond_link)
    }

}