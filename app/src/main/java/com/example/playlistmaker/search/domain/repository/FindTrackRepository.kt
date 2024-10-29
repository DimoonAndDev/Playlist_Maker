package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.Resource
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FindTrackRepository {
    fun findTrack(request: String): Flow<Resource<List<Track>>>

}