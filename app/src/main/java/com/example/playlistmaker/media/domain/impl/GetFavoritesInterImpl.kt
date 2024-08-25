package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.repository.GetFavoritesRep
import com.example.playlistmaker.media.domain.usecase.GetFavoritesInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class GetFavoritesInterImpl(private val getFavoriteRep:GetFavoritesRep):GetFavoritesInteractor {
    override suspend fun getFavoriteTracks(): Flow<List<Track>> {
        return getFavoriteRep.getFavoriteTracks()
    }
}