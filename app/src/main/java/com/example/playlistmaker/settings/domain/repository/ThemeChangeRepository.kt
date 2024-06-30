package com.example.playlistmaker.settings.domain.repository

import android.content.Context

interface ThemeChangeRepository {
    fun changeTheme(context:Context, checked:Boolean)

    fun getTheme(context: Context):Boolean
}