package com.example.playlistmaker.media.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.usecase.GetFavoritesInteractor
import com.example.playlistmaker.media.ui.favorites.models.FavoritesScreenStates
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MediaFavoritesViewModel(private val getFavoritesInteractor: GetFavoritesInteractor) :
    ViewModel() {
    private var getFavoritesJob: Job? = null
    private val favoritesScreenStateLiveData = MutableLiveData<FavoritesScreenStates>(FavoritesScreenStates.Loading)
    fun getFavoritesScreenStateLiveData(): LiveData<FavoritesScreenStates> =
        favoritesScreenStateLiveData

    fun getFavoriteTracks() {
        getFavoritesJob?.cancel()
        getFavoritesJob = viewModelScope.launch {
            getFavoritesInteractor.getFavoriteTracks().collect(){
                if (it.isEmpty()) favoritesScreenStateLiveData.postValue(FavoritesScreenStates.Empty)
                else favoritesScreenStateLiveData.postValue(FavoritesScreenStates.HaveFavorites(it))
            }
        }
    }
}