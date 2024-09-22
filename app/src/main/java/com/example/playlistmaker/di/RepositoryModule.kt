package com.example.playlistmaker.di

import com.example.playlistmaker.media.data.db.converter.PlaylistDBConverter
import com.example.playlistmaker.media.data.db.converter.TrackDbConverter
import com.example.playlistmaker.media.data.db.converter.TrackInPlaylistDBConverter
import com.example.playlistmaker.media.data.repository.FavoriteControlRepositoryImpl
import com.example.playlistmaker.media.data.repository.GetFavoriteTrackRepImpl
import com.example.playlistmaker.media.data.repository.PlaylistControlDBRepositoryImpl
import com.example.playlistmaker.media.domain.repository.GetFavoritesRep
import com.example.playlistmaker.media.domain.repository.PlaylistControlDBRepository
import com.example.playlistmaker.media.player.data.repository.PlayerGetTrackRepositoryImpl
import com.example.playlistmaker.media.player.data.repository.mediaplayer.MediaPlayerRepositoryImpl
import com.example.playlistmaker.media.player.domain.repository.FavoritesControlRepository
import com.example.playlistmaker.media.player.domain.repository.MediaPlayerRepository
import com.example.playlistmaker.media.player.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.media.player.ui.mapper.TrackPlayerTrackMapper
import com.example.playlistmaker.media.playlist_control.data.repository.PlaylistArtRepositroyImpl
import com.example.playlistmaker.media.playlist_control.domain.repository.PlaylistArtRepository
import com.example.playlistmaker.media.playlist_info.data.repository.TrackInPlaylistRepositoryImpl
import com.example.playlistmaker.media.playlist_info.domain.repository.TrackInPlaylistRepository
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
    factory<TrackPlayerTrackMapper> { TrackPlayerTrackMapper() }
    single<PlayerGetTrackRepository> { PlayerGetTrackRepositoryImpl(get(),get()) }
    factory<MediaPlayerRepository> { MediaPlayerRepositoryImpl(get())  }

    //SETTINGS
    single<ThemeChangeRepository> { ThemeChangeRepositoryImpl(get()) }
    single<ExternalNavigator> {ExternalNavigatorImpl(get())  }

    //DB
    factory { TrackDbConverter() }
    single<GetFavoritesRep> { GetFavoriteTrackRepImpl(get(),get()) }
    single<FavoritesControlRepository>{FavoriteControlRepositoryImpl(get(),get()) }

    //Playlist
    factory { PlaylistDBConverter(get())}
    factory { TrackInPlaylistDBConverter(get()) }
    single<PlaylistArtRepository> { PlaylistArtRepositroyImpl(get()) }
    single<PlaylistControlDBRepository> {PlaylistControlDBRepositoryImpl(get(),get(),get())  }
    single<TrackInPlaylistRepository> {TrackInPlaylistRepositoryImpl(get(),get(),get())  }
}

