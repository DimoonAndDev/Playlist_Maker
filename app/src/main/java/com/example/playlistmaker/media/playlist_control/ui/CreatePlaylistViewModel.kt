package com.example.playlistmaker.media.playlist_control.ui

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.usecase.PlaylistControlBDInteractor
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.media.playlist_control.domain.usecases.CreatePlaylistUseCase
import com.example.playlistmaker.media.playlist_control.domain.usecases.PlaylistArtInteractor
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

class CreatePlaylistViewModel(
    private val themeChangeInteractor: ThemeChangeInteractor,
    private val playlistArtInteractor: PlaylistArtInteractor,
    private val createPlaylistUseCase: CreatePlaylistUseCase
) : ViewModel() {
    private var saveJob: Job? = null
    var uriString = ""
    fun getTheme(): Boolean {
        return themeChangeInteractor.getTheme()
    }

    fun savePLArt(uri: Uri):String {
        uriString = uri.toString()
        return playlistArtInteractor.savePLArt(uriString)
    }

    fun savePlaylist(playlist: Playlist){
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            createPlaylistUseCase.createPlaylist(playlist)
        }
    }
}