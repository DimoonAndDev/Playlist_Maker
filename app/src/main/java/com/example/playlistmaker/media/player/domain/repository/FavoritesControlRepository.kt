package com.example.playlistmaker.media.player.domain.repository

import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import kotlinx.coroutines.flow.Flow

interface FavoritesControlRepository {
    suspend fun addTrack(track: PlayerTrack)
    suspend fun deleteTrack(trackId:Int)
    suspend fun checkFavorites(trackId: Int): Flow<PlayerTrack>
}