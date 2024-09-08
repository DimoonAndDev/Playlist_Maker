package com.example.playlistmaker.media.playlist_control.domain.usecases

import com.example.playlistmaker.media.domain.repository.PlaylistControlDBRepository
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist

class CreatePlaylistUseCase(private val playlistControlDBRepository: PlaylistControlDBRepository) {
    suspend fun createPlaylist(playlist: Playlist){
        playlistControlDBRepository.addPlaylist(playlist)
    }
}