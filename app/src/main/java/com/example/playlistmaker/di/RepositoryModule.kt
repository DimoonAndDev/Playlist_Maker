package com.example.playlistmaker.di

import com.example.playlistmaker.player.data.repository.PlayerGetTrackRepositoryImpl
import com.example.playlistmaker.player.data.repository.mediaplayer.MediaPlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.MediaPlayerRepository
import com.example.playlistmaker.player.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.search.data.repository.FindTrackRepositoryImpl
import com.example.playlistmaker.search.data.repository.TrackHistoryRepositoryImpl
import com.example.playlistmaker.search.domain.repository.FindTrackRepository
import com.example.playlistmaker.search.domain.repository.TrackHistoryRepository
import com.example.playlistmaker.settings.data.repository.ThemeChangeRepository
import com.example.playlistmaker.settings.data.repository.ThemeChangeRepositoryImpl
import com.example.playlistmaker.sharing.data.repository.ExternalNavigator
import com.example.playlistmaker.sharing.data.repository.impl.ExternalNavigatorImpl
import org.koin.dsl.module

val repositoryModule = module {
    //SEARCH
    single<FindTrackRepository> {
        FindTrackRepositoryImpl(get())
    }
    single<TrackHistoryRepository> {
        TrackHistoryRepositoryImpl(get())
    }

//PLAYER
    single<PlayerGetTrackRepository> { PlayerGetTrackRepositoryImpl() }
    factory<MediaPlayerRepository> {MediaPlayerRepositoryImpl(get())  }

    //SETTINGS
    single<ThemeChangeRepository> { ThemeChangeRepositoryImpl(get()) }
    single<ExternalNavigator> {ExternalNavigatorImpl(get())  }
}

