package com.example.playlistmaker.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import com.example.playlistmaker.search.domain.usecases.GetSetTrackHistoryInteractor
import com.example.playlistmaker.search.ui.models.SearchScreenState
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragmentViewModel(
    private val findTrackInteractor: FindTrackInteractor,
    private val getSetTrackHistoryInteractor: GetSetTrackHistoryInteractor,
    private val gson:Gson
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
    fun getGsonString(track: Track):String{
        return gson.toJson(track)
    }

    fun clearHistory() {
        getSetTrackHistoryInteractor.clearHistory()
        searchActivityStateLiveData.postValue(
            SearchScreenState.History(
                getSetTrackHistoryInteractor.getHistory()
            )
        )
    }

    private suspend fun findTrack(request: String) {
        if (request.isNotEmpty()) {
            searchActivityStateLiveData.postValue(SearchScreenState.Loading)
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

    private var searchJob: Job?=null

    fun searchDebounce(textValue:String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            findTrack(textValue)
        }
    }
    fun clearSearch(){
        searchJob?.cancel()
    }
    companion object{
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
