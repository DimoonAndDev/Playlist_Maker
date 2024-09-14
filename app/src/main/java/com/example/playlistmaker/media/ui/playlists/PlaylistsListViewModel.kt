package com.example.playlistmaker.media.ui.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.usecase.PlaylistControlBDInteractor
import com.example.playlistmaker.media.playlist_control.domain.usecases.PlaylistArtInteractor
import com.example.playlistmaker.media.ui.playlists.models.PlaylistListScreenStates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlaylistsListViewModel(
    private val playlistControlBDInteractor: PlaylistControlBDInteractor
):ViewModel() {
    private var getPlaylistsJob:Job? = null
    private var deletePlaylistJob:Job? = null
    private val playlistScreenStateLiveData = MutableLiveData<PlaylistListScreenStates>(
        PlaylistListScreenStates.Loading)
    fun getPlaylistScreenStateLiveData():LiveData<PlaylistListScreenStates> = playlistScreenStateLiveData
    fun getPlaylistList(){
        getPlaylistsJob?.cancel()
        getPlaylistsJob = viewModelScope.launch{
            playlistControlBDInteractor.getPlaylistList().collect{
                if (it.isEmpty()) playlistScreenStateLiveData.postValue(PlaylistListScreenStates.Empty)
                else playlistScreenStateLiveData.postValue(PlaylistListScreenStates.HavePlaylists(it))

            }
        }
    }
    fun deletePlaylist(playlistName:String){
        deletePlaylistJob?.cancel()
        deletePlaylistJob = viewModelScope.launch {
            playlistControlBDInteractor.deletePlaylist(playlistName)
        }
    }
//    fun getPLArt(playlistArtUriString: String):String{
//        return playlistArtInteractor.getPLArt(playlistArtUriString)
//    }
}