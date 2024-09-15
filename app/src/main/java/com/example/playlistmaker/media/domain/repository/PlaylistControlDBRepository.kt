package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistControlDBRepository {
    suspend fun addPlaylist(playlist: Playlist)
    suspend fun deletePlaylist(playlistID: Int)
    suspend fun getPlaylists(): Flow<List<Playlist?>>
    suspend fun addTrackToPlaylist(trackRegister:List<Int>,playlistID:Int)
}