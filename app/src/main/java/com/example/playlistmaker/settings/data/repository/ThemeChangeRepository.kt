package com.example.playlistmaker.settings.data.repository



interface ThemeChangeRepository {
    fun changeTheme(checked:Boolean)

    fun getTheme():Boolean
}