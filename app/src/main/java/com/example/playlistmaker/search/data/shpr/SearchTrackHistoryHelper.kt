package com.example.playlistmaker.search.data.shpr

import android.app.Application
import android.content.Context
import com.example.playlistmaker.PLAYLIST_SHARED_PREFS
import com.example.playlistmaker.search.domain.models.Track

import com.google.gson.Gson

const val SP_TRACK_HISTORY_LIST = "SP_TRACK_HISTORY_LIST"

class SearchTrackHistoryHelper : Application() {

    private val trackList = mutableListOf<Track>()
    private val maxHistoryTrack = 10
    fun saveTrack(context: Context, track: Track) {
        val sharedPrefs = context.getSharedPreferences(
            PLAYLIST_SHARED_PREFS, MODE_PRIVATE
        )
        val tempArray = Gson().fromJson(
            sharedPrefs.getString(SP_TRACK_HISTORY_LIST, "")?:"",
            Array<Track>::class.java
        )
        if (tempArray!= null) trackList.addAll(tempArray)
        val iterator = trackList.iterator()
        while (iterator.hasNext()){
            if (iterator.next().trackId == track.trackId) iterator.remove()
        }
        if (trackList.size == maxHistoryTrack) trackList.removeAt(9)
        trackList.add(0,track)
        val savedStringTrackList = Gson().toJson(trackList)
        sharedPrefs.edit()?.putString(SP_TRACK_HISTORY_LIST, savedStringTrackList)?.apply()
    }

    fun getHistory(context: Context): MutableList<Track> {
        trackList.clear()
        val sharedPrefs = context.getSharedPreferences(
            PLAYLIST_SHARED_PREFS, MODE_PRIVATE
        )
        val tempArray = Gson().fromJson(
            sharedPrefs.getString(SP_TRACK_HISTORY_LIST, "")?:"",
            Array<Track>::class.java
        )
        if (tempArray!= null) trackList.addAll(tempArray)
        return trackList
    }
    fun clearHistory(context: Context){
        trackList.clear()
        val sharedPrefs = context.getSharedPreferences(
            PLAYLIST_SHARED_PREFS, MODE_PRIVATE
        )
        val emptyStringTrackList = Gson().toJson(trackList)
        sharedPrefs.edit()?.putString(SP_TRACK_HISTORY_LIST,emptyStringTrackList)?.apply()

    }
}