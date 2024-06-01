package com.example.playlistmaker.data.dto

import android.media.MediaPlayer

object MediaPlayerInstance {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var mediaPlayerStatus = MediaPlayerStatus.STATE_DEFAULT

    fun get():MediaPlayerStatusDTO {
            return MediaPlayerStatusDTO(mediaPlayer, mediaPlayerStatus)
        }
}