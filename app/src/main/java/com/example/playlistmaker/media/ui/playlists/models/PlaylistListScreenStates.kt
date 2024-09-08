package com.example.playlistmaker.media.ui.playlists.models

import com.example.playlistmaker.media.playlist_control.domain.models.Playlist


sealed class PlaylistListScreenStates{
    object Empty:PlaylistListScreenStates()
    object Loading:PlaylistListScreenStates()
    data class HavePlaylists(val listOfPlaylists:List<Playlist?>):PlaylistListScreenStates()
}
