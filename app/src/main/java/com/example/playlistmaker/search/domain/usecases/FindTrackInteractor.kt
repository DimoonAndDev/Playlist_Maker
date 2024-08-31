package com.example.playlistmaker.search.domain.usecases

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow


interface FindTrackInteractor {
    fun findTrack(request:String):Flow<Pair<List<Track>?,String?>>

}