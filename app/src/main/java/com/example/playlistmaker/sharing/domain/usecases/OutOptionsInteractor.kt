package com.example.playlistmaker.sharing.domain.usecases

import android.content.Intent
import com.example.playlistmaker.settings.domain.models.IntentModel
import com.example.playlistmaker.settings.domain.repository.OutOptionsRepository

class OutOptionsInteractor(private val outOptionsRepository: OutOptionsRepository) {
    fun openTerms():IntentModel{
        return outOptionsRepository.openTerms()
    }
    fun shareApp():IntentModel{
        return outOptionsRepository.shareApp()
    }
    fun writeSupport():IntentModel{
        return outOptionsRepository.writeSupport()
    }

}