package com.example.playlistmaker.di

import com.example.playlistmaker.media.domain.usecase.CreatePlaylistUseCase
import com.example.playlistmaker.media.domain.usecase.GetFavoritesInteractor
import com.example.playlistmaker.media.domain.usecase.PlaylistArtInteractor
import com.example.playlistmaker.media.domain.usecase.PlaylistControlDBInteractor
import com.example.playlistmaker.media.domain.usecase.TrackInPlaylistInteractror
import com.example.playlistmaker.media.domain.usecase.player.FavoritesControlInteractor
import com.example.playlistmaker.media.domain.usecase.player.GetPlayerTrackUseCase
import com.example.playlistmaker.media.domain.usecase.player.MediaPlayerInteractor
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
    single<GetSetTrackHistoryInteractor> { GetSetTrackHistoryInteractor(get()) }

    //PLAYER
    single<GetPlayerTrackUseCase> { GetPlayerTrackUseCase(get()) }
    factory<MediaPlayerInteractor> { MediaPlayerInteractor(get()) }

    //SETTINGS
    single<ThemeChangeInteractor> { ThemeChangeInteractorImpl(get()) }
    single<SharingInteractor> { SharingInteractorImpl(get()) }

    //DB
    single<GetFavoritesInteractor> { GetFavoritesInteractor(get()) }
    single<FavoritesControlInteractor> { FavoritesControlInteractor(get()) }

    //Playlist
    single<PlaylistArtInteractor> { PlaylistArtInteractor(get()) }
    single<PlaylistControlDBInteractor> { PlaylistControlDBInteractor(get()) }
    single<CreatePlaylistUseCase> { CreatePlaylistUseCase(get()) }
    single<TrackInPlaylistInteractror> { TrackInPlaylistInteractror(get()) }
}