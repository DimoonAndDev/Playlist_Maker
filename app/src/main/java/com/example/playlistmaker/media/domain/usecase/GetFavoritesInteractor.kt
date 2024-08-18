package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface GetFavoritesInteractor {
    fun getFavoriteTracks():Flow<List<Track>>
}