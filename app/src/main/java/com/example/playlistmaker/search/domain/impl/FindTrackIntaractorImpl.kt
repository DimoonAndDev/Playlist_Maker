package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.Resource
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.FindTrackRepository
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FindTrackIntaractorImpl(val repository: FindTrackRepository) : FindTrackInteractor {

    override fun findTrack(request: String): Flow<Pair<List<Track>?, String?>> {
        return repository.findTrack(request).map {
            when (it) {
                is Resource.Success -> {
                    Pair(it.data, null)
                }

                is Resource.Error -> {
                    Pair(null, it.message)
                }
            }
        }

    }


}