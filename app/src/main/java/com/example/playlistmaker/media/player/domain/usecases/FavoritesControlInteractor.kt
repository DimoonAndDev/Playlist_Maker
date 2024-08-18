package com.example.playlistmaker.media.player.domain.usecases

import com.example.playlistmaker.media.player.domain.repository.FavoritesControlRepository
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoritesControlInteractor(private val favoritesControlRepository: FavoritesControlRepository) {
    suspend fun addTrack(track: Track){
        favoritesControlRepository.addTrack(track)
    }
    suspend fun deleteTrack(trackId:Int){
        favoritesControlRepository.deleteTrack(trackId)
    }
    suspend fun checkFavorites(trackId: Int): Flow<Track> {
        return favoritesControlRepository.checkFavorites(trackId)
    }
}