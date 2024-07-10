package com.example.playlistmaker.sharing.data.repository

import com.example.playlistmaker.settings.domain.models.IntentModel

interface ExternalNavigator {
    fun openTerms():IntentModel
    fun shareApp():IntentModel
    fun writeSupport():IntentModel
}