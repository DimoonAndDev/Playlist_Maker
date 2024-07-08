package com.example.playlistmaker.settings.domain.usecases

import android.content.Context
import com.example.playlistmaker.settings.domain.repository.ThemeChangeRepository

class ThemeChangeInteractor(private val themeChangeRepository: ThemeChangeRepository) {
    fun changeTheme(checked:Boolean){
        return themeChangeRepository.changeTheme(checked)
    }
    fun getTheme():Boolean{
        return themeChangeRepository.getTheme()
    }
}