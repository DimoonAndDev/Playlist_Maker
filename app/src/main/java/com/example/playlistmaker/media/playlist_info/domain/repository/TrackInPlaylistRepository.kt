package com.example.playlistmaker.media.playlist_info.domain.repository

import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackInPlaylistRepository {
    suspend fun getTracksInPlaylist(playlist: Playlist): Flow<List<Track>>
    suspend fun deleteTrackFromPlaylist(trackId:Int,playlist: Playlist):Playlist
}