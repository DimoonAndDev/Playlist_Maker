package com.example.playlistmaker.search.data.repository


import android.app.Application
import com.example.playlistmaker.search.data.shpr.SearchTrackHistoryHelper
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.TrackHistoryRepository

class TrackHistoryRepositoryImpl(val application: Application):TrackHistoryRepository {
    private val searchTrackHistoryHelper = SearchTrackHistoryHelper()
    override fun saveTrackHistory(track: Track) {
        searchTrackHistoryHelper.saveTrack(application,track)
    }

    override fun getHistory(): List<Track> {
        return searchTrackHistoryHelper.getHistory(application)
    }

    override fun clearHistory() {
        searchTrackHistoryHelper.clearHistory(application)
    }
}