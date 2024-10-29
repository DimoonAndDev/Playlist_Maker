package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.domain.repository.TrackInPlaylistRepository
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class TrackInPlaylistInteractror(private val trackInPlaylistRepository: TrackInPlaylistRepository) {

    suspend fun getTracksInPlaylist(playlist: Playlist): Flow<List<Track>> {
        return trackInPlaylistRepository.getTracksInPlaylist(playlist)
    }

    suspend fun deleteTrackFromPlaylist(trackID: Int, playlist: Playlist): Playlist {
        return trackInPlaylistRepository.deleteTrackFromPlaylist(trackID, playlist)
    }
}