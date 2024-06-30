package com.example.playlistmaker.settings.domain.usecases

import android.content.Context
import com.example.playlistmaker.settings.domain.repository.ThemeChangeRepository

class ThemeChangeInteractor(private val themeChangeRepository: ThemeChangeRepository) {
    fun changeTheme(context: Context,checked:Boolean){
        return themeChangeRepository.changeTheme(context, checked)
    }
    fun getTheme(context: Context):Boolean{
        return themeChangeRepository.getTheme(context)
    }
}