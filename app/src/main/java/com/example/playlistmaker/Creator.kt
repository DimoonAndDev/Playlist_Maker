package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.player.data.repository.PlayerGetTrackRepositoryImpl
import com.example.playlistmaker.player.data.repository.mediaplayer.MediaPlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.player.domain.repository.MediaPlayerRepository
import com.example.playlistmaker.player.domain.usecases.GetPlayerTrackUseCase
import com.example.playlistmaker.player.domain.usecases.MediaPlayerInteractor
import com.example.playlistmaker.settings.data.repository.ThemeChangeInteractorImpl
import com.example.playlistmaker.settings.domain.repository.OutOptionsRepository
import com.example.playlistmaker.settings.domain.repository.ThemeChangeRepository
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor
import com.example.playlistmaker.sharing.data.repository.OutOptionsRepositoryImpl
import com.example.playlistmaker.sharing.domain.usecases.OutOptionsInteractor

object Creator {
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


    fun provideThemeChangeInteractor():ThemeChangeInteractor{
        return ThemeChangeInteractor(provideThemeChangeRepository())
    }
    private fun provideThemeChangeRepository(): ThemeChangeRepository {
        return ThemeChangeInteractorImpl()
    }

    fun provideOutOptionsInteractor(application: Application):OutOptionsInteractor{
        return OutOptionsInteractor(provideOutOptionsRepository(application))
    }
    private fun provideOutOptionsRepository(application: Application):OutOptionsRepository{
        return OutOptionsRepositoryImpl(application)
    }

}