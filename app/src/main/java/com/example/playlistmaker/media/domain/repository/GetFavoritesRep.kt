package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface GetFavoritesRep {
    suspend fun getFavoriteTracks(): Flow<List<Track>>
}