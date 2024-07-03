package com.example.playlistmaker

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

const val PLAYLIST_SHARED_PREFS = "PLAYLIST_SHARED_PREFS"
const val SP_THEME_KEY = "SP_THEME_KEY"

class App() : Application() {
    private var darkTheme = false
    override fun onCreate() {
        super.onCreate()


        val sharedPrefs = applicationContext.getSharedPreferences(
            PLAYLIST_SHARED_PREFS, MODE_PRIVATE
        )
        darkTheme = sharedPrefs.getBoolean(
            SP_THEME_KEY,
            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        )
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

        )


    }

    fun getThemeStatus(): Boolean {
        return darkTheme
    }


}
