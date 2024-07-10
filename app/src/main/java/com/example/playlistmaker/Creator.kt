package com.example.playlistmaker

import android.app.Application
import android.content.Context
import com.example.playlistmaker.player.data.repository.PlayerGetTrackRepositoryImpl
import com.example.playlistmaker.player.data.repository.mediaplayer.MediaPlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.player.domain.repository.MediaPlayerRepository
import com.example.playlistmaker.player.domain.usecases.GetPlayerTrackUseCase
import com.example.playlistmaker.player.domain.usecases.MediaPlayerInteractor
import com.example.playlistmaker.search.data.repository.FindTrackRepositoryImpl
import com.example.playlistmaker.search.data.repository.TrackHistoryRepositoryImpl
import com.example.playlistmaker.search.domain.impl.FindTrackIntaractorImpl
import com.example.playlistmaker.search.domain.repository.FindTrackRepository
import com.example.playlistmaker.search.domain.repository.TrackHistoryRepository
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import com.example.playlistmaker.search.domain.usecases.GetSetTrackHistoryInteractor
import com.example.playlistmaker.settings.data.repository.ThemeChangeRepositoryImpl
import com.example.playlistmaker.sharing.data.repository.ExternalNavigator
import com.example.playlistmaker.settings.data.repository.ThemeChangeRepository
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractorImpl
import com.example.playlistmaker.sharing.data.repository.ExternalNavigatorImpl
import com.example.playlistmaker.sharing.domain.usecases.SharingInteractor
import com.example.playlistmaker.sharing.domain.usecases.SharingInteractorImpl
private lateinit var applicationCr: Application
object Creator {
   //PLAYER_ACTIVITY
    fun provideGetPlayerTrackUseCase(): GetPlayerTrackUseCase {
        return GetPlayerTrackUseCase(providePlayerGetTrackRepository())
    }

    fun provideMediaPlayerInteractor(): MediaPlayerInteractor {
        return MediaPlayerInteractor(
            provideMediaPlayerRepository()
        )
    }

    private fun providePlayerGetTrackRepository(): PlayerGetTrackRepository {
        return PlayerGetTrackRepositoryImpl()
    }

    private fun provideMediaPlayerRepository(): MediaPlayerRepository {
        return MediaPlayerRepositoryImpl()
    }

//SETTINGS_ACTIVITY
    fun provideThemeChangeInteractor(): ThemeChangeInteractor {
        return ThemeChangeInteractorImpl(provideThemeChangeRepository())
    }
    private fun provideThemeChangeRepository(): ThemeChangeRepository {
        return ThemeChangeRepositoryImpl()
    }

    fun provideSharingInteractor(application: Application): SharingInteractor {
        return SharingInteractorImpl(provideOutOptionsRepository(application))
    }
    private fun provideOutOptionsRepository(application: Application): ExternalNavigator {
        return ExternalNavigatorImpl(application)
    }
//SEARCH_ACTIVITY
    fun provideGetSetTrackHistoryInteractor():GetSetTrackHistoryInteractor{
        return GetSetTrackHistoryInteractor(provideTrackHistoryRepository())
    }
    private fun provideTrackHistoryRepository():TrackHistoryRepository{
        return TrackHistoryRepositoryImpl(applicationCr)
    }
    fun provideFindTrackInteractor(): FindTrackInteractor{
        return FindTrackIntaractorImpl(provideFindTrackRepository())
    }
    private fun provideFindTrackRepository():FindTrackRepository{
        return FindTrackRepositoryImpl()
    }
    fun initApplication(application: Application){
        applicationCr=application
    }
    fun getApplication():Context{
        return applicationCr
    }
}