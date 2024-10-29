package com.example.playlistmaker.di

import com.example.playlistmaker.media.ui.favorites.MediaFavoritesViewModel
import com.example.playlistmaker.media.ui.player.PlayTrackFragmentViewModel
import com.example.playlistmaker.media.ui.playlists.pl_create.CreatePlaylistViewModel
import com.example.playlistmaker.media.ui.playlists.pl_create.EditPlaylistViewModel
import com.example.playlistmaker.media.ui.playlists.pl_info.PlaylistInfoFragmentViewModel
import com.example.playlistmaker.media.ui.playlists.pl_list.PlaylistsListViewModel
import com.example.playlistmaker.search.ui.SearchFragmentViewModel
import com.example.playlistmaker.settings.ui.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PlayTrackFragmentViewModel(get(), get(), get(), get(), get())
    }
    viewModel {
        SearchFragmentViewModel(get(), get(), get())
    }
    viewModel {
        SettingViewModel(get(), get())
    }
    viewModel {
        MediaFavoritesViewModel(get(), get())
    }
    viewModel {
        PlaylistsListViewModel(get(), get())
    }
    viewModel {
        CreatePlaylistViewModel(get(), get(), get())
    }
    viewModel {
        PlaylistInfoFragmentViewModel(get(), get(), get())
    }
    viewModel {
        EditPlaylistViewModel(get(), get(), get(), get(), get())
    }
}