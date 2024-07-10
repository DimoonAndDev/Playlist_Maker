package com.example.playlistmaker.sharing.domain.usecases

import com.example.playlistmaker.settings.domain.models.IntentModel
import com.example.playlistmaker.sharing.data.repository.ExternalNavigator

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator):SharingInteractor {
    override fun openTerms():IntentModel{
        return externalNavigator.openTerms()
    }
    override fun shareApp():IntentModel{
        return externalNavigator.shareApp()
    }
    override fun writeSupport():IntentModel{
        return externalNavigator.writeSupport()
    }

}