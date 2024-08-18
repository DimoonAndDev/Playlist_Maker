package com.example.playlistmaker.media.player.domain.repository

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesControlRepository {
    suspend fun addTrack(track: Track)
    suspend fun deleteTrack(trackId:Int)
    suspend fun checkFavorites(trackId: Int): Flow<Track>
}