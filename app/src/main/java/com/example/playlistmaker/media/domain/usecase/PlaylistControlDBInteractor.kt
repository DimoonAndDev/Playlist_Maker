package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.domain.repository.PlaylistControlDBRepository
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlaylistControlDBInteractor(private val playlistControlDBRepository: PlaylistControlDBRepository) {
    suspend fun getPlaylistList(): Flow<List<Playlist?>> {
        return playlistControlDBRepository.getPlaylists()
    }

    suspend fun deletePlaylist(playlistId: Int) {
        playlistControlDBRepository.deletePlaylist(playlistId)
    }

    suspend fun addTrackToPlaylist(trackRegister: List<Int>, playlistId: Int, track: Track) {
        playlistControlDBRepository.addTrackToPlaylist(trackRegister, playlistId, track)
    }

    suspend fun getUpdatedPlaylist(playlistId: Int): Flow<Playlist?> {
        return playlistControlDBRepository.getUpdatedPlaylist(playlistId)
    }

    suspend fun updatePlaylist(playlist: Playlist) {
        playlistControlDBRepository.updatePlaylist(playlist)
    }
}