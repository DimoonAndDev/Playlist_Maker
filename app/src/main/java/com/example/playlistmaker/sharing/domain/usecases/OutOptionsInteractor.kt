package com.example.playlistmaker.sharing.domain.usecases

import android.content.Intent
import com.example.playlistmaker.settings.domain.repository.OutOptionsRepository

class OutOptionsInteractor(private val outOptionsRepository: OutOptionsRepository) {
    fun openTerms():Intent{
        return outOptionsRepository.openTerms()
    }
    fun shareApp():Intent{
        return outOptionsRepository.shareApp()
    }
    fun writeSupport():Intent{
        return outOptionsRepository.writeSupport()
    }

}