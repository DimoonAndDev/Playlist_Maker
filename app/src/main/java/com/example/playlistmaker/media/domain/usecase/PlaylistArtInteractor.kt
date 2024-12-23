package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.media.domain.repository.PlaylistArtRepository

class PlaylistArtInteractor(private val playlistArtRepository: PlaylistArtRepository) {
    fun savePLArt(uriString: String): String {
        return playlistArtRepository.savePLArt(uriString)
    }

    suspend fun getPLArt(playlistArtUriString: String): String {
        return playlistArtRepository.getPLArt(playlistArtUriString)
    }
}