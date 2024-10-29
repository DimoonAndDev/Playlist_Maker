package com.example.playlistmaker.search.ui.models

import com.example.playlistmaker.search.domain.models.Track

sealed class SearchScreenState {
    object Loading : SearchScreenState()
    object NoResult : SearchScreenState()
    data class NoConnection(val lastRequest: String?) : SearchScreenState()

    data class History(val historyList: List<Track>) : SearchScreenState()
    data class FoundContent(val foundTracks: List<Track>) : SearchScreenState()
}