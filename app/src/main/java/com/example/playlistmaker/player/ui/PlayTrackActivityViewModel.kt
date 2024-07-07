package com.example.playlistmaker.player.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.Creator
import com.example.playlistmaker.player.data.dto.MediaPlayerStatus
import com.example.playlistmaker.player.domain.usecases.MediaPlayerInteractor
import com.example.playlistmaker.player.ui.mapper.PlayerStatusMapper
import com.example.playlistmaker.player.ui.models.PlayerStatus
import com.example.playlistmaker.player.ui.models.PlayerTrack

class PlayTrackActivityViewModel(
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    track: PlayerTrack
) : ViewModel() {
    private var seconds = 0L
    private val DELAY = 500L
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    private var mediaPlayerLiveData = MutableLiveData(MediaPlayerStatus.STATE_DEFAULT.status)
    private val playerTimerLiveData = MutableLiveData(0L)

    fun getMediaPlayerLiveData(): LiveData<Int> = mediaPlayerLiveData
    fun getTimerLiveData(): LiveData<Long> = playerTimerLiveData

    private fun updateMediaPlayerState(state: Int) {
        if (mediaPlayerLiveData.value != state)
            mediaPlayerLiveData.postValue(state)
    }

    fun clickMediaPlayerControl() {
        updateMediaPlayerState(mediaPlayerInteractor.getStatus())
        when (PlayerStatusMapper.map(mediaPlayerLiveData.value)) {
            PlayerStatus.STATE_PLAYING -> {
                pauseTrack()
            }

            PlayerStatus.STATE_PAUSED, PlayerStatus.STATE_PREPARED -> {
                playTrack()
            }

            else -> {}
        }
    }

    fun releasePlayer() {
        mediaPlayerInteractor.releasePlayer()
    }

    fun getPlayerStatus(): Int {
        updateMediaPlayerState(mediaPlayerInteractor.getStatus())
        return mediaPlayerLiveData.value ?: 0
    }

    private fun playTrack() {
        mediaPlayerInteractor.clickPlayTrack()
        updateMediaPlayerState(MediaPlayerStatus.STATE_PLAYING.status)
        startTrackTimer()
    }

    fun pauseTrack() {
        mediaPlayerInteractor.clickPauseTrack()
        updateMediaPlayerState(MediaPlayerStatus.STATE_PAUSED.status)
    }

    fun preparePlayer(dataSourse: String) {
        mediaPlayerInteractor.preparePlayer(dataSourse)
        mediaPlayerInteractor.setOnPrepareListener {
            updateMediaPlayerState(MediaPlayerStatus.STATE_PREPARED.status)
        }

    }
    private fun startTrackTimer() {
        var startTime = System.currentTimeMillis()
        if (seconds != 0L) startTime -= (seconds * 1000)
        mainThreadHandler.post(
            createUpdateTimerTask(startTime)
        )
    }
    private fun createUpdateTimerTask(startTime: Long): Runnable {
        return object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                if (PlayerStatusMapper.map(getPlayerStatus()) == PlayerStatus.STATE_PLAYING) {
                    seconds = elapsedTime / 1000
                    playerTimerLiveData.postValue(seconds)
                    mainThreadHandler.postDelayed(this, DELAY)
                }
                if (PlayerStatusMapper.map(getPlayerStatus()) == PlayerStatus.STATE_PREPARED) {
                    seconds = 0L
                    playerTimerLiveData.postValue(seconds)
                }
            }
        }
    }
    companion object {
        fun getViewModelFactory(track: PlayerTrack): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayTrackActivityViewModel(Creator.provideMediaPlayerInteractor(), track)
            }

        }
    }

}