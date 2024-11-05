package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.search.domain.models.Track

interface TrackHistoryRepository {
    fun saveTrackHistory(track: Track)

    fun getHistory(): List<Track>
    fun clearHistory()
}