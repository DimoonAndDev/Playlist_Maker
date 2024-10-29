package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.media.domain.repository.GetFavoritesRep
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class GetFavoritesInteractor(private val getFavoriteRep: GetFavoritesRep) {
    suspend fun getFavoriteTracks(): Flow<List<Track?>> {
        return getFavoriteRep.getFavoriteTracks()
    }
}