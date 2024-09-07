package com.example.playlistmaker.di

import com.example.playlistmaker.media.ui.favorites.MediaFavoritesViewModel
import com.example.playlistmaker.media.ui.playlists.MediaPlaylistsViewModel
import com.example.playlistmaker.media.player.ui.PlayTrackFragmentViewModel
import com.example.playlistmaker.search.ui.SearchFragmentViewModel
import com.example.playlistmaker.settings.ui.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PlayTrackFragmentViewModel(get(),get(),get())
    }
    viewModel {
        SearchFragmentViewModel(get(),get(),get())
    }
    viewModel {
       SettingViewModel(get(),get())
    }
    viewModel{
        MediaFavoritesViewModel(get(),get())
    }
    viewModel{
        MediaPlaylistsViewModel()
    }
}