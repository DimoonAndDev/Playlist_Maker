package com.example.playlistmaker.sharing.domain.usecases

import com.example.playlistmaker.settings.domain.models.IntentModel

interface SharingInteractor {
    fun openTerms(): IntentModel
    fun shareApp(): IntentModel
    fun writeSupport(): IntentModel
}