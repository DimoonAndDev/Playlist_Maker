package com.example.playlistmaker.settings.domain.repository

import com.example.playlistmaker.settings.domain.models.IntentModel

interface OutOptionsRepository {
    fun openTerms():IntentModel
    fun shareApp():IntentModel
    fun writeSupport():IntentModel
}