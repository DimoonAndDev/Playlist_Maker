package com.example.playlistmaker.search.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import com.example.playlistmaker.search.domain.usecases.GetSetTrackHistoryInteractor
import com.example.playlistmaker.search.ui.models.SearchScreenState

class SearchActivityViewModel(
    private val findTrackInteractor: FindTrackInteractor,
    private val getSetTrackHistoryInteractor: GetSetTrackHistoryInteractor
) : ViewModel() {

    private var searchActivityStateLiveData = MutableLiveData<SearchScreenState>(
        SearchScreenState.History(
            getSetTrackHistoryInteractor.getHistory()
        )
    )

    fun getSearchActivityStateLiveData(): LiveData<SearchScreenState> = searchActivityStateLiveData

    fun getHistory(): List<Track> {
        searchActivityStateLiveData.postValue(
            SearchScreenState.History(
                getSetTrackHistoryInteractor.getHistory()
            )
        )
        return getSetTrackHistoryInteractor.getHistory()
    }

    fun clearHistory() {
        getSetTrackHistoryInteractor.clearHistory()
        searchActivityStateLiveData.postValue(
            SearchScreenState.History(
                getSetTrackHistoryInteractor.getHistory()
            )
        )
    }

    fun findTrack(request: String) {
        if (request.isNotEmpty()) {
            searchActivityStateLiveData.postValue(SearchScreenState.Loading)
            findTrackInteractor.findTrack(request, object : FindTrackInteractor.SearchConsumer {
                override fun consume(foundTracks: List<Track>?, errorMessage: String?) {
                    if (foundTracks == null) {
                        searchActivityStateLiveData.postValue(
                            SearchScreenState.NoConnection(
                                errorMessage
                            )
                        )
                    } else if (foundTracks.isEmpty()) {
                        searchActivityStateLiveData.postValue(SearchScreenState.NoResult)
                    } else {
                        searchActivityStateLiveData.postValue(
                            SearchScreenState.FoundContent(
                                foundTracks
                            )
                        )
                    }

                }
            })
        }
    }




}