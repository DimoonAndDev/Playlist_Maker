package com.example.playlistmaker.player.data.repository.mediaplayer

import android.media.MediaPlayer
import com.example.playlistmaker.player.data.dto.MediaPlayerStatus
import com.example.playlistmaker.player.domain.repository.MediaPlayerRepository


class MediaPlayerRepositoryImpl: MediaPlayerRepository {
    private val mediaPlayer = MediaPlayer()
    private var playerStatus = MediaPlayerStatus.STATE_DEFAULT
    override fun preparePlayer(dataSource:String) {
        mediaPlayer.setDataSource(dataSource)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener(){
            playerStatus = MediaPlayerStatus.STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerStatus = MediaPlayerStatus.STATE_PREPARED
        }
    }

    override fun playTrack() {
        mediaPlayer.start()
        playerStatus = MediaPlayerStatus.STATE_PLAYING
    }

    override fun pauseTrack() {
        mediaPlayer.pause()
        playerStatus= MediaPlayerStatus.STATE_PAUSED
    }

    override fun releasePlayer() {
        mediaPlayer.release()
        playerStatus = MediaPlayerStatus.STATE_DEFAULT
    }

    override fun getStatus(): Int {
        return playerStatus.status
    }

}