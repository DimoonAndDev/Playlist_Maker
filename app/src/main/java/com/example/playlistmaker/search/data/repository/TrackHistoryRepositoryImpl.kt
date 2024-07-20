package com.example.playlistmaker.search.data.repository



import com.example.playlistmaker.search.data.shpr.SearchTrackHistoryHelper
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.TrackHistoryRepository

class TrackHistoryRepositoryImpl(private val searchTrackHistoryHelper:SearchTrackHistoryHelper):TrackHistoryRepository {

    override fun saveTrackHistory(track: Track) {
        searchTrackHistoryHelper.saveTrack(track)
    }

    override fun getHistory(): List<Track> {
        return searchTrackHistoryHelper.getHistory()
    }

    override fun clearHistory() {
        searchTrackHistoryHelper.clearHistory()
    }
}