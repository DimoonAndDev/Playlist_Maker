package com.example.playlistmaker.settings.domain.repository



interface ThemeChangeRepository {
    fun changeTheme(checked:Boolean)

    fun getTheme():Boolean
}