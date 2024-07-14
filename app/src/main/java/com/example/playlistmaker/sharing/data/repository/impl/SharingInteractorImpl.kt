package com.example.playlistmaker.sharing.data.repository.impl

import com.example.playlistmaker.sharing.data.repository.ExternalNavigator
import com.example.playlistmaker.sharing.domain.usecases.SharingInteractor

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator) : SharingInteractor {
    override fun openTerms() {
        externalNavigator.openTerms()
    }

    override fun shareApp() {
        externalNavigator.shareApp()
    }

    override fun writeSupport() {
        externalNavigator.writeSupport()
    }



}