package com.example.playlistmaker.domain.usecases

import android.media.MediaPlayer
import com.example.playlistmaker.domain.repository.mediaplayer.DeactivatePlayerRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PauseTrackRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PlayTrackRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PreparePlayerRepository

class MediaPlayerInteractor(
    private val pauseTrackRepository: PauseTrackRepository,
    private val playTrackRepository: PlayTrackRepository,
    private val preparePlayerRepository: PreparePlayerRepository,
    private val deactivatePlayerRepository: DeactivatePlayerRepository
) {
    fun playTrack(mediaPlayer: MediaPlayer) {
        playTrackRepository.playTrack(mediaPlayer)

    }

    fun pauseTrack(mediaPlayer: MediaPlayer) {
        pauseTrackRepository.pauseTrack(mediaPlayer)
    }
    fun preparePlayer():MediaPlayer{
        return preparePlayerRepository.preparePlayer()

    }
    fun deactivatePlayer(mediaPlayer: MediaPlayer){
        deactivatePlayerRepository.deactivatePlayer(mediaPlayer)

    }
}