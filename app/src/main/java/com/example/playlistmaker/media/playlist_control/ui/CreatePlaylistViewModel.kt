package com.example.playlistmaker.media.playlist_control.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.media.playlist_control.domain.usecases.CreatePlaylistUseCase
import com.example.playlistmaker.media.playlist_control.domain.usecases.PlaylistArtInteractor
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class CreatePlaylistViewModel(
    private val themeChangeInteractor: ThemeChangeInteractor,
    private val playlistArtInteractor: PlaylistArtInteractor,
    private val createPlaylistUseCase: CreatePlaylistUseCase
) : ViewModel() {
    private var saveJob: Job? = null
    open var uriString = ""
    open fun getTheme(): Boolean {
        return themeChangeInteractor.getTheme()
    }

    open fun savePLArt(uri: Uri):String {
        uriString = uri.toString()
        return playlistArtInteractor.savePLArt(uriString)
    }

    open fun savePlaylist(playlist: Playlist){
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            createPlaylistUseCase.createPlaylist(playlist)
        }
    }
}