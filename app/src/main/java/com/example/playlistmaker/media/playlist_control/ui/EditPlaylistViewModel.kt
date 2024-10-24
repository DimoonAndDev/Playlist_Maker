package com.example.playlistmaker.media.playlist_control.ui

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.usecase.PlaylistControlBDInteractor
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.media.playlist_control.domain.usecases.CreatePlaylistUseCase
import com.example.playlistmaker.media.playlist_control.domain.usecases.PlaylistArtInteractor
import com.example.playlistmaker.settings.domain.usecases.ThemeChangeInteractor
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditPlaylistViewModel (
    private val themeChangeInteractor: ThemeChangeInteractor,
    private val playlistArtInteractor: PlaylistArtInteractor,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val gson: Gson,
    private val playlistControlBDInteractor: PlaylistControlBDInteractor
) : CreatePlaylistViewModel(themeChangeInteractor,playlistArtInteractor,createPlaylistUseCase) {
    private var saveJob: Job? = null
    override var uriString = ""
    override fun getTheme(): Boolean {
        return themeChangeInteractor.getTheme()
    }

    override fun savePLArt(uri: Uri):String {
        uriString = uri.toString()
        return playlistArtInteractor.savePLArt(uriString)
    }

    fun updatePlaylist(playlist: Playlist){
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            playlistControlBDInteractor.deletePlaylist(playlist.innerId)
            createPlaylistUseCase.createPlaylist(playlist)
        }
    }
    fun getPlfromJson(plJson:String):Playlist{
        return gson.fromJson(plJson,Playlist::class.java)
    }
    fun plToJson(playlist: Playlist):String{
        return gson.toJson(playlist)
    }


}