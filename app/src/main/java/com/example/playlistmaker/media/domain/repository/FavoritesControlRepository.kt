package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.media.ui.player.models.PlayerTrack
import kotlinx.coroutines.flow.Flow

interface FavoritesControlRepository {
    suspend fun addTrack(track: PlayerTrack)
    suspend fun deleteTrack(trackId: Int)
    suspend fun checkFavorites(trackId: Int): Flow<PlayerTrack?>
}