package com.example.playlistmaker.searchrecycler

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.playlistmaker.PLAYLIST_SHARED_PREFS
import com.example.playlistmaker.SP_THEME_KEY
import com.example.playlistmaker.Track
import com.google.gson.Gson

const val PLAYLIST_TRACKHISTORY_SHARED_PREFS = "PLAYLIST_SHARED_PREFS"
const val SP_TRACK_HISTORY_LIST = "SP_TRACK_HISTORY_LIST"

class SearchTrackHistoryHelper : Application() {

    private val trackList = mutableListOf<Track>()

    fun saveTrack(context: Context,track: Track) {
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
        trackList.add(track)
        if (trackList.size > 10) trackList.removeAt(0)
        sharedPrefs.edit()?.putString(SP_TRACK_HISTORY_LIST, Gson().toJson(trackList))?.apply()
    }

    fun getHistory(context:Context): MutableList<Track> {
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
        sharedPrefs.edit()?.putString(SP_TRACK_HISTORY_LIST, Gson().toJson(trackList))?.apply()

    }
}