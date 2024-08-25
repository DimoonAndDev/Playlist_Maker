package com.example.playlistmaker.media.domain.usecase

import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface GetFavoritesInteractor {
    suspend fun getFavoriteTracks():Flow<List<Track>>
}