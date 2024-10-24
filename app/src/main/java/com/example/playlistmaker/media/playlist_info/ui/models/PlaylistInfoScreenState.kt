package com.example.playlistmaker.media.playlist_info.ui.models

import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track

data class PlaylistInfoScreenState(val playlist: Playlist,val trackList:List<Track>)
