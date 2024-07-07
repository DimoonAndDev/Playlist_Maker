package com.example.playlistmaker.settings.data.repository

import android.app.Application
import android.content.Context
import com.example.playlistmaker.App
import com.example.playlistmaker.PLAYLIST_SHARED_PREFS
import com.example.playlistmaker.SP_THEME_KEY
import com.example.playlistmaker.settings.domain.repository.ThemeChangeRepository

class ThemeChangeInteractorImpl : ThemeChangeRepository {

    override fun changeTheme(context: Context,checked:Boolean) {
        val sharedPrefs = context.getSharedPreferences(
            PLAYLIST_SHARED_PREFS, Application.MODE_PRIVATE
        )
        sharedPrefs.edit().putBoolean(SP_THEME_KEY,checked).apply()
                    }

    override fun getTheme(context: Context): Boolean {
        return (context as App).getThemeStatus()
    }
}