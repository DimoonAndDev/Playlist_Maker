package com.example.playlistmaker.media.ui.favorites.models

import com.example.playlistmaker.search.domain.models.Track

sealed class FavoritesScreenStates{
    object Empty:FavoritesScreenStates()
    object Loading:FavoritesScreenStates()
    data class HaveFavorites(val listOfFavoriteTracks:List<Track?>):FavoritesScreenStates()
}
