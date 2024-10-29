package com.example.playlistmaker.search.domain.usecases

import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.TrackHistoryRepository

class GetSetTrackHistoryInteractor(private val trackHistoryRepository: TrackHistoryRepository) {
    fun saveTrack(track: Track) {
        trackHistoryRepository.saveTrackHistory(track)
    }

    fun getHistory(): List<Track> {
        return trackHistoryRepository.getHistory()
    }

    fun clearHistory() {
        trackHistoryRepository.clearHistory()
    }
}