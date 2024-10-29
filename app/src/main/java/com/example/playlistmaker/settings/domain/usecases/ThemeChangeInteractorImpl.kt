package com.example.playlistmaker.settings.domain.usecases

import com.example.playlistmaker.settings.data.repository.ThemeChangeRepository

class ThemeChangeInteractorImpl(private val themeChangeRepository: ThemeChangeRepository) :
    ThemeChangeInteractor {
    override fun changeTheme(checked: Boolean) {
        return themeChangeRepository.changeTheme(checked)
    }

    override fun getTheme(): Boolean {
        return themeChangeRepository.getTheme()
    }
}