package com.example.playlistmaker.media.playlist_info.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.usecase.PlaylistControlBDInteractor
import com.example.playlistmaker.media.playlist_control.domain.models.Playlist
import com.example.playlistmaker.media.playlist_info.domain.usecases.TrackInPlaylistInteractror
import com.example.playlistmaker.media.playlist_info.ui.models.PlaylistInfoScreenState
import com.example.playlistmaker.search.domain.models.Track
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistInfoFragmentViewModel(
    private val gson: Gson,
    private val trackInPlaylistInteractror: TrackInPlaylistInteractror
) : ViewModel() {
    private var lastTracksInPlaylist: List<Track> = listOf()
    private var lastPlaylist: Playlist = Playlist("")
    private val screenStateLiveData = MutableLiveData<PlaylistInfoScreenState>()

    fun getScreenStateLiveData(): LiveData<PlaylistInfoScreenState> = screenStateLiveData


    private var deleteJob: Job? = null
    private var getTracksJob: Job? = null


    fun getPlaylist(playlistGson: String?): Playlist {
        lastPlaylist = gson.fromJson(playlistGson, Playlist::class.java)
        return lastPlaylist
    }

    fun getTracksInPlaylist(playlist: Playlist) {
        getTracksJob?.cancel()
        getTracksJob = viewModelScope.launch(Dispatchers.IO) {
            trackInPlaylistInteractror.getTracksInPlaylist(playlist).collect() {
                val trackListWithoutNulls = it.filterNotNull()
                lastPlaylist = playlist
                lastTracksInPlaylist = it
                screenStateLiveData.postValue(PlaylistInfoScreenState(lastPlaylist, it))
            }
        }
    }

    fun getTrackJson(track: Track): String {
        return gson.toJson(track)
    }

    fun deleteTrackFromPlaylist(track: Track, playlist: Playlist) {
        deleteJob?.cancel()
        deleteJob = viewModelScope.launch(Dispatchers.IO) {
            lastPlaylist =
                trackInPlaylistInteractror.deleteTrackFromPlaylist(track.trackId, playlist)
            getTracksInPlaylist(lastPlaylist)
        }
    }


}











