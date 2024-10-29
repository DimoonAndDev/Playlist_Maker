package com.example.playlistmaker.media.data.repository.player

import android.media.MediaPlayer
import com.example.playlistmaker.media.data.dto.MediaPlayerStatus
import com.example.playlistmaker.media.domain.repository.MediaPlayerRepository


class MediaPlayerRepositoryImpl(private val mediaPlayer: MediaPlayer) : MediaPlayerRepository {
    private var playerStatus = MediaPlayerStatus.STATE_DEFAULT
    override fun preparePlayer(dataSource: String) {

        if (playerStatus == MediaPlayerStatus.STATE_DEFAULT) {
            mediaPlayer.setDataSource(dataSource)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                playerStatus = MediaPlayerStatus.STATE_PREPARED

            }
            mediaPlayer.setOnCompletionListener {
                playerStatus = MediaPlayerStatus.STATE_PREPARED
            }

        }

    }


    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun playTrack() {
        playerStatus = MediaPlayerStatus.STATE_PLAYING
        mediaPlayer.start()

    }

    override fun pauseTrack() {
        playerStatus = MediaPlayerStatus.STATE_PAUSED
        mediaPlayer.pause()

    }

    override fun releasePlayer() {
        playerStatus = MediaPlayerStatus.STATE_DEFAULT
        mediaPlayer.release()

    }

    override fun getStatus(): Int {
        return playerStatus.status

    }


}