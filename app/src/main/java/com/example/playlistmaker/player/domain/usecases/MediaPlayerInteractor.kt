package com.example.playlistmaker.player.domain.usecases

import com.example.playlistmaker.player.domain.repository.MediaPlayerRepository

class MediaPlayerInteractor(
    private val mediaPlayerRepository: MediaPlayerRepository,
) {
    fun playTrack() {
        mediaPlayerRepository.playTrack()
    }

    fun pauseTrack() {
        mediaPlayerRepository.pauseTrack()
    }

    fun preparePlayer(dataSource: String) {
        mediaPlayerRepository.preparePlayer(dataSource)
    }

    fun releasePlayer() {
        mediaPlayerRepository.releasePlayer()

    }

    fun getStatus(): Int {
        return mediaPlayerRepository.getStatus()
    }

    fun getCurrentPosition(): Int{
        return mediaPlayerRepository.getCurrentPosition()
    }

}