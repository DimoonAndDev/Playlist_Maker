package com.example.playlistmaker.media.ui.playlists.pl_info

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.models.Playlist
import com.example.playlistmaker.media.domain.usecase.PlaylistControlDBInteractor
import com.example.playlistmaker.media.domain.usecase.TrackInPlaylistInteractror
import com.example.playlistmaker.media.ui.playlists.models.PlaylistInfoScreenState
import com.example.playlistmaker.search.domain.models.Track
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistInfoFragmentViewModel(
    private val gson: Gson,
    private val trackInPlaylistInteractror: TrackInPlaylistInteractror,
    private val playlistControlDBInteractor: PlaylistControlDBInteractor
) : ViewModel() {
    private var lastTracksInPlaylist: List<Track> = listOf()
    private var lastPlaylist: Playlist = Playlist("testt")
    private val screenStateLiveData = MutableLiveData<PlaylistInfoScreenState>()

    fun getScreenStateLiveData(): LiveData<PlaylistInfoScreenState> = screenStateLiveData


    private var deleteJob: Job? = null
    private var getCheckTracksJob: Job? = null


    fun getPlaylistFromJsonWithCheck(playlistGson: String?): Playlist {
        val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
        val playlistFromJson = gson.fromJson(playlistGson, Playlist::class.java)
        getCheckTracksJob?.cancel()
        getCheckTracksJob = viewModelScope.launch(dispatcherIO) {
            playlistControlDBInteractor.getUpdatedPlaylist(playlistFromJson.innerId).collect {
                lastPlaylist = it ?: playlistFromJson

            }
            trackInPlaylistInteractror.getTracksInPlaylist(lastPlaylist).collect {
                lastTracksInPlaylist = it
            }
            screenStateLiveData.postValue(
                PlaylistInfoScreenState(
                    lastPlaylist,
                    lastTracksInPlaylist
                )
            )
        }
        return playlistFromJson
    }

    fun getTracksInPlaylist(playlist: Playlist) {
        val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
        getCheckTracksJob?.cancel()
        getCheckTracksJob = viewModelScope.launch(dispatcherIO) {
            trackInPlaylistInteractror.getTracksInPlaylist(playlist).collect {
                lastTracksInPlaylist = it
            }
            screenStateLiveData.postValue(
                PlaylistInfoScreenState(
                    lastPlaylist,
                    lastTracksInPlaylist
                )
            )
        }
    }

    fun getTrackJson(track: Track): String {
        return gson.toJson(track)
    }

    fun deleteTrackFromPlaylist(trackId: Int, playlist: Playlist) {
        val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
        deleteJob?.cancel()
        deleteJob = viewModelScope.launch(dispatcherIO) {
            lastPlaylist =
                trackInPlaylistInteractror.deleteTrackFromPlaylist(trackId, playlist)
            getTracksInPlaylist(lastPlaylist)
        }
    }

    fun sharePlaylist(context: Context, trackNumberString: String) {
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
        viewModelScope.launch {
            for (track in playlist.tracksRegister) {
                deleteTrackFromPlaylist(track, playlist)
            }
            playlistControlDBInteractor.deletePlaylist(playlist.innerId)
        }
    }

    fun plToJson(playlist: Playlist): String {
        return gson.toJson(playlist)
    }
}