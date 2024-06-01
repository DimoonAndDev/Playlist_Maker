package com.example.playlistmaker.domain.repository.mediaplayer

import android.media.MediaPlayer

interface PlayTrackRepository {
    fun playTrack(mediaPlayer: MediaPlayer)
}