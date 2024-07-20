package com.example.playlistmaker.settings.data.repository

import android.app.Application
import android.content.Context
import com.example.playlistmaker.App
import com.example.playlistmaker.PLAYLIST_SHARED_PREFS
import com.example.playlistmaker.SP_THEME_KEY

class ThemeChangeRepositoryImpl(private val contextApp:Context) : ThemeChangeRepository {

    override fun changeTheme(checked:Boolean) {
        val sharedPrefs = contextApp.getSharedPreferences(
            PLAYLIST_SHARED_PREFS, Application.MODE_PRIVATE
        )
        sharedPrefs.edit().putBoolean(SP_THEME_KEY,checked).apply()
                    }

    override fun getTheme(): Boolean {
        return (contextApp as App).getThemeStatus()
    }
}