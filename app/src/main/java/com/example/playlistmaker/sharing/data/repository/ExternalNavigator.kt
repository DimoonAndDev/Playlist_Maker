package com.example.playlistmaker.sharing.data.repository

import com.example.playlistmaker.sharing.domain.models.EmailDetails

interface ExternalNavigator {
    fun openTerms(termLink:String)
    fun shareApp(shareLink:String)
    fun writeSupport(emailDetails: EmailDetails)
}