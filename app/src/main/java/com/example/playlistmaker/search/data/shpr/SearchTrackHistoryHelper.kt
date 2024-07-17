package com.example.playlistmaker.search.data.shpr


import android.content.SharedPreferences
import com.example.playlistmaker.search.domain.models.Track
import com.google.gson.Gson
import org.koin.java.KoinJavaComponent.getKoin


const val SP_TRACK_HISTORY_LIST = "SP_TRACK_HISTORY_LIST"

class SearchTrackHistoryHelper  {

    private val trackList = mutableListOf<Track>()
    private val maxHistoryTrack = 10
    private val sharedPrefs = getKoin().get<SharedPreferences>()

    fun saveTrack(track: Track) {

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

    fun getHistory(): MutableList<Track> {
        trackList.clear()
        val tempArray = Gson().fromJson(
            sharedPrefs.getString(SP_TRACK_HISTORY_LIST, "")?:"",
            Array<Track>::class.java
        )
        if (tempArray!= null) trackList.addAll(tempArray)
        return trackList
    }
    fun clearHistory(){
        trackList.clear()
        val emptyStringTrackList = Gson().toJson(trackList)
        sharedPrefs.edit()?.putString(SP_TRACK_HISTORY_LIST,emptyStringTrackList)?.apply()

    }
}