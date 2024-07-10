package com.example.playlistmaker.settings.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.Creator
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor
import com.example.playlistmaker.sharing.domain.usecases.SharingInteractor



class SettingViewModel(
    private val themeChangeInteractor: ThemeChangeInteractor,
    private val sharingInteractor: SharingInteractor,
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

    fun openTerms(){
       sharingInteractor.openTerms()
    }

    fun shareApp(){
        sharingInteractor.shareApp()
    }
    fun writeSupport(){
        sharingInteractor.writeSupport()
    }
companion object{
    fun getViewModelFactory(application: Application):ViewModelProvider.Factory = viewModelFactory {
        initializer {
            SettingViewModel(Creator.provideThemeChangeInteractor(),
                Creator.provideSharingInteractor(application))
        }
    }
}
}