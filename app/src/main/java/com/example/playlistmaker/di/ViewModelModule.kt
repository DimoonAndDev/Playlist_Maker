package com.example.playlistmaker.di

import com.example.playlistmaker.media.ui.MediaFavoritesViewModel
import com.example.playlistmaker.media.ui.MediaPlaylistsViewModel
import com.example.playlistmaker.media.player.ui.PlayTrackActivityViewModel
import com.example.playlistmaker.search.ui.SearchActivityViewModel
import com.example.playlistmaker.settings.ui.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PlayTrackActivityViewModel(get(),get())
    }
    viewModel {
        SearchActivityViewModel(get(),get())
    }
    viewModel {
       SettingViewModel(get(),get())
    }
    viewModel{
        MediaFavoritesViewModel()
    }
    viewModel{
        MediaPlaylistsViewModel()
    }
}