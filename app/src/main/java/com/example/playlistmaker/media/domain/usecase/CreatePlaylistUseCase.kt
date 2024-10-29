package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.domain.repository.PlaylistControlDBRepository

class CreatePlaylistUseCase(private val playlistControlDBRepository: PlaylistControlDBRepository) {
    suspend fun createPlaylist(playlist: Playlist) {
        playlistControlDBRepository.addPlaylist(playlist)
    }
}