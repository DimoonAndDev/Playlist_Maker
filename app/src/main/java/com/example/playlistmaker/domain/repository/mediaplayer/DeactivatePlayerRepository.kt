package com.example.playlistmaker.domain.repository.mediaplayer

import android.media.MediaPlayer

interface DeactivatePlayerRepository {
    fun deactivatePlayer(mediaPlayer: MediaPlayer)
}