package com.example.playlistmaker.di

import com.example.playlistmaker.media.player.domain.usecases.GetPlayerTrackUseCase
import com.example.playlistmaker.media.player.domain.usecases.MediaPlayerInteractor
import com.example.playlistmaker.search.domain.impl.FindTrackIntaractorImpl
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import com.example.playlistmaker.search.domain.usecases.GetSetTrackHistoryInteractor
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractorImpl
import com.example.playlistmaker.sharing.data.repository.impl.SharingInteractorImpl
import com.example.playlistmaker.sharing.domain.usecases.SharingInteractor
import org.koin.dsl.module

val interactorModule = module {
    //SEARCH
    single<FindTrackInteractor> { FindTrackIntaractorImpl(get()) }
    single<GetSetTrackHistoryInteractor> { GetSetTrackHistoryInteractor(get())  }

    //PLAYER
    single<GetPlayerTrackUseCase> { GetPlayerTrackUseCase(get())  }
    factory<MediaPlayerInteractor> { MediaPlayerInteractor(get())  }

    //SETTINGS
    single<ThemeChangeInteractor> { ThemeChangeInteractorImpl(get())  }
    single<SharingInteractor> {SharingInteractorImpl(get())  }
}