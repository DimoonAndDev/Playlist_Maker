package com.example.playlistmaker.media.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.player.data.dto.MediaPlayerStatus
import com.example.playlistmaker.media.player.domain.usecases.FavoritesControlInteractor
import com.example.playlistmaker.media.player.domain.usecases.GetPlayerTrackUseCase
import com.example.playlistmaker.media.player.domain.usecases.MediaPlayerInteractor
import com.example.playlistmaker.media.player.ui.mapper.PlayerStatusMapper
import com.example.playlistmaker.media.player.ui.models.PlayerStatus
import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayTrackActivityViewModel(
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    private val getPlayerTrackUseCase: GetPlayerTrackUseCase,
    private val favoritesControlInteractor: FavoritesControlInteractor
) : ViewModel() {
    private val DELAY = 300L
    private var timerJob: Job? = null

    private var mediaPlayerLiveData = MutableLiveData(MediaPlayerStatus.STATE_DEFAULT.status)
    private val playerTimerLiveData = MutableLiveData(0L)
    private val favoriteLiveData = MutableLiveData(false)

    fun getMediaPlayerLiveData(): LiveData<Int> = mediaPlayerLiveData
    fun getTimerLiveData(): LiveData<Long> = playerTimerLiveData
    fun getFavoriteLiveData(): LiveData<Boolean> = favoriteLiveData

    private var favoriteCheckJob: Job? = null
    private var favoriteControlJob: Job? = null
    fun checkFavorite(trackId: Int) {
        favoriteCheckJob?.cancel()
        favoriteCheckJob = viewModelScope.launch {
            favoritesControlInteractor.checkFavorites(trackId).collect() {
                if (it == null) favoriteLiveData.postValue(false)
                else favoriteLiveData.postValue(true)
            }
        }
    }

    fun favoriteClickControl(track: PlayerTrack){
        favoriteCheckJob?.cancel()
        favoriteControlJob?.cancel()
        favoriteControlJob = viewModelScope.launch {
            if (favoriteLiveData.value == false){
                favoritesControlInteractor.addTrack(track)
                favoriteLiveData.postValue(true)
            } else {
                favoritesControlInteractor.deleteTrack(track.trackId)
                favoriteLiveData.postValue(false)
            }
        }
    }

    private fun updateMediaPlayerState(state: Int) {
        if (mediaPlayerLiveData.value != state)
            mediaPlayerLiveData.postValue(state)
    }

    fun getPlayerTrack(trackGson: String?): PlayerTrack {
        val track = getPlayerTrackUseCase.execute(trackGson)
        preparePlayer(track.previewUrl)
        return track
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
        return mediaPlayerInteractor.getStatus()
    }

    private fun playTrack() {
        updateMediaPlayerState(MediaPlayerStatus.STATE_PLAYING.status)
        mediaPlayerInteractor.playTrack()
        startTrackTimer()
    }

    fun pauseTrack() {
        updateMediaPlayerState(MediaPlayerStatus.STATE_PAUSED.status)
        mediaPlayerInteractor.pauseTrack()

    }

    private fun getCurrentPosition(): Int {
        return mediaPlayerInteractor.getCurrentPosition() / 1000
    }

    private fun preparePlayer(dataSourse: String) {
        mediaPlayerInteractor.preparePlayer(dataSourse)
        updateMediaPlayerState(MediaPlayerStatus.STATE_PREPARED.status)

    }

    private fun startTrackTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (PlayerStatusMapper.map(getPlayerStatus()) == PlayerStatus.STATE_PLAYING) {
                playerTimerLiveData.postValue(getCurrentPosition().toLong())
                delay(DELAY)
            }
            if (PlayerStatusMapper.map(getPlayerStatus()) == PlayerStatus.STATE_PREPARED) {
                updateMediaPlayerState(MediaPlayerStatus.STATE_PREPARED.status)
                playerTimerLiveData.postValue(0L)

            }

        }
    }
}











