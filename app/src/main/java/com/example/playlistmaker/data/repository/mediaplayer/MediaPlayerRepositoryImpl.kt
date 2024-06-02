package com.example.playlistmaker.data.repository.mediaplayer

import android.media.MediaPlayer
import android.os.Looper
import com.example.playlistmaker.domain.repository.mediaplayer.MediaPlayerRepository
import android.os.Handler
import com.example.playlistmaker.data.dto.MediaPlayerStatus
import com.example.playlistmaker.presentation.models.PlayerStatus

class MediaPlayerRepositoryImpl:MediaPlayerRepository {
    private val mediaPlayer = MediaPlayer()
    private val mainHandler = Handler(Looper.getMainLooper())
    private var playerStatus = MediaPlayerStatus.STATE_DEFAULT
    override fun preparePlayer(trackURL:String) {
        mediaPlayer.setDataSource(trackURL)
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
        playerStatus=MediaPlayerStatus.STATE_PAUSED
    }

    override fun releasePlayer() {
        mediaPlayer.release()
        playerStatus = MediaPlayerStatus.STATE_DEFAULT
    }

    override fun getStatus(): Int {
        return playerStatus.status
    }

}