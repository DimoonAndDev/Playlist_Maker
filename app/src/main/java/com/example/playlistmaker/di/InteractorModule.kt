package com.example.playlistmaker.di

import com.example.playlistmaker.media.domain.impl.GetFavoritesInterImpl
import com.example.playlistmaker.media.domain.usecase.GetFavoritesInteractor
import com.example.playlistmaker.media.domain.usecase.PlaylistControlBDInteractor
import com.example.playlistmaker.media.player.domain.usecases.FavoritesControlInteractor
import com.example.playlistmaker.media.player.domain.usecases.GetPlayerTrackUseCase
import com.example.playlistmaker.media.player.domain.usecases.MediaPlayerInteractor
import com.example.playlistmaker.media.playlist_control.domain.usecases.CreatePlaylistUseCase
import com.example.playlistmaker.media.playlist_control.domain.usecases.PlaylistArtInteractor
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

    //DB
    single<GetFavoritesInteractor>{GetFavoritesInterImpl(get())}
    single<FavoritesControlInteractor>{FavoritesControlInteractor(get())}

    //Playlist
    single<PlaylistArtInteractor> {PlaylistArtInteractor(get())}
    single<PlaylistControlBDInteractor> { PlaylistControlBDInteractor(get())  }
    single<CreatePlaylistUseCase> { CreatePlaylistUseCase(get())  }
}