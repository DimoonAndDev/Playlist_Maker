package com.example.playlistmaker.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import com.example.playlistmaker.search.domain.usecases.GetSetTrackHistoryInteractor
import com.example.playlistmaker.search.ui.models.SearchScreenState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

    fun saveTrackInHistory(track: Track) {
        getSetTrackHistoryInteractor.saveTrack(track)
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
            viewModelScope.launch {
                findTrackInteractor.findTrack(request).collect {
                    if (it.first == null) {
                        searchActivityStateLiveData.postValue(
                            SearchScreenState.NoConnection(
                                it.second
                            )
                        )
                    } else if (it.first!!.isEmpty()) {
                        searchActivityStateLiveData.postValue(SearchScreenState.NoResult)
                    } else {
                        searchActivityStateLiveData.postValue(
                            SearchScreenState.FoundContent(
                                it.first!!
                            )
                        )
                    }
                }
            }

        }
    }


}