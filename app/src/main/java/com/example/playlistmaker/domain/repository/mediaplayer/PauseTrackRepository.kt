package com.example.playlistmaker.domain.repository.mediaplayer

import android.media.MediaPlayer

interface PauseTrackRepository {
    fun pauseTrack(mediaPlayer: MediaPlayer)
}