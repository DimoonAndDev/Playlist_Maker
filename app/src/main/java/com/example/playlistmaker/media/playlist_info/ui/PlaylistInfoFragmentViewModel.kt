package com.example.playlistmaker.media.playlist_info.ui

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.media.domain.usecase.PlaylistControlBDInteractor
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.google.gson.Gson

class PlaylistInfoFragmentViewModel(
    private val gson: Gson,
    private val playlistControlBDInteractor: PlaylistControlBDInteractor
) : ViewModel() {


    fun getPlaylist(playlistGson: String?): Playlist {
        return gson.fromJson(playlistGson, Playlist::class.java)
    }

}











