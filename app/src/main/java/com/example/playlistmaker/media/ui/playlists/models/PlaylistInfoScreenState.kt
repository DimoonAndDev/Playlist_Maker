package com.example.playlistmaker.media.ui.playlists.models

import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track

data class PlaylistInfoScreenState(val playlist: Playlist, val trackList: List<Track>)
