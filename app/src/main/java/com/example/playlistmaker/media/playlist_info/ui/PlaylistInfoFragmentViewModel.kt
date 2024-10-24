package com.example.playlistmaker.media.playlist_info.ui

import android.content.Context
import android.content.Intent
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistInfoFragmentViewModel(
    private val gson: Gson,
    private val trackInPlaylistInteractror: TrackInPlaylistInteractror,
    private val playlistControlBDInteractor: PlaylistControlBDInteractor
) : ViewModel() {
    private var lastTracksInPlaylist: List<Track> = listOf()
    private var lastPlaylist: Playlist = Playlist("")
    private val screenStateLiveData = MutableLiveData<PlaylistInfoScreenState>()

    fun getScreenStateLiveData(): LiveData<PlaylistInfoScreenState> = screenStateLiveData


    private var deleteJob: Job? = null
    private var getTracksJob: Job? = null


    fun getPlaylistFromJson(playlistGson: String?): Playlist {
        lastPlaylist = gson.fromJson(playlistGson, Playlist::class.java)
        return lastPlaylist
    }

    fun getTracksInPlaylist(playlist: Playlist) {
        getTracksJob?.cancel()
        getTracksJob = viewModelScope.launch(Dispatchers.IO) {
            trackInPlaylistInteractror.getTracksInPlaylist(playlist).collect {
                lastPlaylist = playlist
                lastTracksInPlaylist = it
                screenStateLiveData.postValue(
                    PlaylistInfoScreenState(
                        lastPlaylist,
                        lastTracksInPlaylist
                    )
                )
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

    fun sharePlaylist(context: Context) {
        val trackNumber = lastTracksInPlaylist.size
        val trackNumberString =
            if (trackNumber % 100 == 11 || trackNumber % 100 == 12) "$trackNumber треков"
            else {
                when (trackNumber % 10) {
                    0, 5, 6, 7, 8, 9 -> "$trackNumber треков"
                    1 -> "$trackNumber трек"
                    else -> "$trackNumber трека"
                }
            }
        var messageString = "${lastPlaylist.name}\n${lastPlaylist.description}\n$trackNumberString"
        var trackCount = 1
        for (track in lastTracksInPlaylist) {
            messageString += "\n$trackCount. ${track.artistName} - ${track.trackName} (${
                SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(track.trackTimeMillis)
            })"
            trackCount++
        }
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, messageString)
            type = "text/plain"
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch { playlistControlBDInteractor.deletePlaylist(playlist.innerId) }
    }
    fun plToJson(playlist: Playlist):String{
        return gson.toJson(playlist)
    }


}











