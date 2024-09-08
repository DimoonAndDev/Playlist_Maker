package com.example.playlistmaker.media.playlist_control.domain.usecases

import com.example.playlistmaker.media.playlist_control.domain.repository.PlaylistArtRepository

class PlaylistArtInteractor(private val playlistArtRepository: PlaylistArtRepository) {
    fun savePLArt(uriString:String){
        playlistArtRepository.savePLArt(uriString)
    }
}