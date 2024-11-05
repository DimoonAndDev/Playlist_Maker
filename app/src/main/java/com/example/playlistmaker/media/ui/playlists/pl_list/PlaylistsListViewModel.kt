package com.example.playlistmaker.media.ui.playlists.pl_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.domain.usecase.PlaylistControlDBInteractor
import com.example.playlistmaker.media.ui.playlists.models.PlaylistListScreenStates
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlaylistsListViewModel(
    private val playlistControlDBInteractor: PlaylistControlDBInteractor,
    private val gson: Gson
) : ViewModel() {
    private var getPlaylistsJob: Job? = null
    private var deletePlaylistJob: Job? = null
    private val playlistScreenStateLiveData = MutableLiveData<PlaylistListScreenStates>(
        PlaylistListScreenStates.Loading
    )

    fun getPlaylistScreenStateLiveData(): LiveData<PlaylistListScreenStates> =
        playlistScreenStateLiveData

    fun getPlaylistList() {
        getPlaylistsJob?.cancel()
        getPlaylistsJob = viewModelScope.launch {
            playlistControlDBInteractor.getPlaylistList().collect {
                if (it.isEmpty()) playlistScreenStateLiveData.postValue(PlaylistListScreenStates.Empty)
                else playlistScreenStateLiveData.postValue(PlaylistListScreenStates.HavePlaylists(it))

            }
        }
    }

    fun convertPLtoJson(playlist: Playlist): String {
        return gson.toJson(playlist)
    }

}