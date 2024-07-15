package com.example.playlistmaker.settings.domain.usecases

interface ThemeChangeInteractor {
    fun changeTheme(checked: Boolean)
    fun getTheme(): Boolean
}