package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface GetFavoritesRep {
    fun getFavoriteTracks(): Flow<List<Track>>
}