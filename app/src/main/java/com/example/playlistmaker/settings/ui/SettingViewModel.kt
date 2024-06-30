package com.example.playlistmaker.settings.ui

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.App
import com.example.playlistmaker.Creator
import com.example.playlistmaker.sharing.domain.usecases.OutOptionsInteractor
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor

class SettingViewModel(
    private val themeChangeInteractor: ThemeChangeInteractor,
    private val outOptionsInteractor: OutOptionsInteractor,
    private val application:Application
):AndroidViewModel(application) {
    private val themeLiveData = MutableLiveData(themeChangeInteractor.getTheme(application))

    init {
        themeChangeInteractor.changeTheme(application,themeLiveData.value?:false)
    }
    fun getThemeLiveData():LiveData<Boolean> = themeLiveData

    fun changeThemeLD(darkTheme:Boolean){
        themeLiveData.postValue(darkTheme)
        themeChangeInteractor.changeTheme(application,darkTheme)
    }

    fun getThemeLD():Boolean = themeChangeInteractor.getTheme(application)

    fun openTerms():Intent{
       return outOptionsInteractor.openTerms()
    }

    fun shareApp():Intent{
        return outOptionsInteractor.shareApp()
    }
    fun writeSupport():Intent{
        return outOptionsInteractor.writeSupport()
    }
companion object{
    fun getViewModelFactory(application: Application):ViewModelProvider.Factory = viewModelFactory {
        initializer {
            SettingViewModel(Creator.provideThemeChangeInteractor(),
                Creator.provideOutOptionsInteractor(application), application)
        }
    }
}
}