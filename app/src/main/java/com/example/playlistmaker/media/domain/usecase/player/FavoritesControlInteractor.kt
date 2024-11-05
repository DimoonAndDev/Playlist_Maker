package com.example.playlistmaker.media.domain.usecase.player

import com.example.playlistmaker.media.domain.repository.FavoritesControlRepository
import com.example.playlistmaker.media.ui.player.models.PlayerTrack
import kotlinx.coroutines.flow.Flow

class FavoritesControlInteractor(private val favoritesControlRepository: FavoritesControlRepository) {
    suspend fun addTrack(track: PlayerTrack) {
        favoritesControlRepository.addTrack(track)
    }

    suspend fun deleteTrack(trackId: Int) {
        favoritesControlRepository.deleteTrack(trackId)
    }

    suspend fun checkFavorites(trackId: Int): Flow<PlayerTrack?> {
        return favoritesControlRepository.checkFavorites(trackId)
    }
}