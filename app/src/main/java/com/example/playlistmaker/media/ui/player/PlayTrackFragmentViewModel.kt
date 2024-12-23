package com.example.playlistmaker.media.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.domain.usecase.PlaylistControlDBInteractor
import com.example.playlistmaker.media.domain.usecase.player.FavoritesControlInteractor
import com.example.playlistmaker.media.domain.usecase.player.GetPlayerTrackUseCase
import com.example.playlistmaker.media.domain.usecase.player.MediaPlayerInteractor
import com.example.playlistmaker.media.ui.player.mapper.PlayerStatusMapper
import com.example.playlistmaker.media.ui.player.mapper.TrackPlayerTrackMapper
import com.example.playlistmaker.media.ui.player.models.PlayerStatus
import com.example.playlistmaker.media.ui.player.models.PlayerTrack
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayTrackFragmentViewModel(
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    private val getPlayerTrackUseCase: GetPlayerTrackUseCase,
    private val favoritesControlInteractor: FavoritesControlInteractor,
    private val playlistControlDBInteractor: PlaylistControlDBInteractor,
    private val trackPlayerTrackMapper: TrackPlayerTrackMapper
) : ViewModel() {
    private val DELAY = 300L
    private var timerJob: Job? = null

    private var mediaPlayerLiveData = MutableLiveData(PlayerStatus.STATE_DEFAULT.status)
    private val playerTimerLiveData = MutableLiveData(0L)
    private val favoriteLiveData = MutableLiveData(false)
    private val playlistListBottomsheetLiveData = MutableLiveData<List<Playlist>>()

    fun getMediaPlayerLiveData(): LiveData<Int> = mediaPlayerLiveData
    fun getTimerLiveData(): LiveData<Long> = playerTimerLiveData
    fun getFavoriteLiveData(): LiveData<Boolean> = favoriteLiveData
    fun getPlaylistListBottomsheetLiveData(): LiveData<List<Playlist>> =
        playlistListBottomsheetLiveData

    private var favoriteCheckJob: Job? = null
    private var favoriteControlJob: Job? = null
    private var playerJob: Job? = null
    fun checkFavorite(trackId: Int) {
        favoriteCheckJob?.cancel()
        favoriteCheckJob = viewModelScope.launch {
            favoritesControlInteractor.checkFavorites(trackId).collect {
                if (it == null) favoriteLiveData.postValue(false)
                else favoriteLiveData.postValue(true)
            }
        }
    }

    fun favoriteClickControl(track: PlayerTrack) {
        favoriteCheckJob?.cancel()
        favoriteControlJob?.cancel()
        favoriteControlJob = viewModelScope.launch {
            if (favoriteLiveData.value == false) {
                favoritesControlInteractor.addTrack(track)
                favoriteLiveData.postValue(true)
            } else {
                favoritesControlInteractor.deleteTrack(track.trackId)
                favoriteLiveData.postValue(false)
            }
        }
    }

    fun addTrackToPlaylist(track: PlayerTrack, playlist: Playlist): Boolean {
        if (playlist.tracksRegister.contains(track.trackId)) return true
        val newPlaylistTrackRegister = mutableListOf<Int>()
        newPlaylistTrackRegister.addAll(playlist.tracksRegister)
        newPlaylistTrackRegister.add(track.trackId)
        viewModelScope.launch {
            playlistControlDBInteractor.addTrackToPlaylist(
                newPlaylistTrackRegister,
                playlist.innerId,
                trackPlayerTrackMapper.map(track)
            )
            getPlaylistList()
        }
        return false
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
        updateMediaPlayerState(PlayerStatus.STATE_PLAYING.status)
        mediaPlayerInteractor.playTrack()
        startTrackTimer()
    }

    fun pauseTrack() {
        updateMediaPlayerState(PlayerStatus.STATE_PAUSED.status)
        mediaPlayerInteractor.pauseTrack()

    }

    private fun getCurrentPosition(): Int {
        return mediaPlayerInteractor.getCurrentPosition() / 1000
    }

    fun preparePlayer(dataSourse: String) {
        mediaPlayerInteractor.preparePlayer(dataSourse)
    }

    private fun startTrackTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (PlayerStatusMapper.map(getPlayerStatus()) == PlayerStatus.STATE_PLAYING) {
                playerTimerLiveData.postValue(getCurrentPosition().toLong())
                delay(DELAY)
            }
            if (PlayerStatusMapper.map(getPlayerStatus()) == PlayerStatus.STATE_PREPARED) {
                updateMediaPlayerState(PlayerStatus.STATE_PREPARED.status)
                playerTimerLiveData.postValue(0L)

            }

        }
    }

    fun getPlaylistList() {
        viewModelScope.launch {
            playlistControlDBInteractor.getPlaylistList().collect {
                val playlistsWithoutNulls = it.filterNotNull()
                playlistListBottomsheetLiveData.postValue(playlistsWithoutNulls)

            }
        }
    }
}











