package com.example.playlistmaker.settings.ui

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.Creator
import com.example.playlistmaker.sharing.domain.usecases.OutOptionsInteractor
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor

class SettingViewModel(
    private val themeChangeInteractor: ThemeChangeInteractor,
    private val outOptionsInteractor: OutOptionsInteractor,
    ): ViewModel() {
    private val themeLiveData = MutableLiveData(themeChangeInteractor.getTheme())

    init {
        themeChangeInteractor.changeTheme(themeLiveData.value?:false)
    }
    fun getThemeLiveData():LiveData<Boolean> = themeLiveData

    fun changeThemeLD(darkTheme:Boolean){
        themeLiveData.postValue(darkTheme)
        themeChangeInteractor.changeTheme(darkTheme)
    }

    fun getThemeLD():Boolean = themeChangeInteractor.getTheme()

    fun openTerms():Intent{
       return IntentFormatter.format(outOptionsInteractor.openTerms())
    }

    fun shareApp():Intent{
        return IntentFormatter.format(outOptionsInteractor.shareApp())
    }
    fun writeSupport():Intent{
        return IntentFormatter.format(outOptionsInteractor.writeSupport())
    }
companion object{
    fun getViewModelFactory(application: Application):ViewModelProvider.Factory = viewModelFactory {
        initializer {
            SettingViewModel(Creator.provideThemeChangeInteractor(),
                Creator.provideOutOptionsInteractor(application))
        }
    }
}
}