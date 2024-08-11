package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.Resource
import com.example.playlistmaker.search.data.dto.SearchRequest
import com.example.playlistmaker.search.data.dto.SearchResponse
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.FindTrackRepository
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindTrackRepositoryImpl(private val retrofitNetworkClient:RetrofitNetworkClient) : FindTrackRepository {

    override fun findTrack(
        request: String
    ): Flow<Resource<List<Track>>> = flow {

        val response = retrofitNetworkClient.doRequest(SearchRequest(request))
        when (response.resultCode) {
            -1 -> emit(Resource.Error(request))
            200 -> emit(Resource.Success((response as SearchResponse).results.map {
                Track(
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.trackId,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                )
            }))

            else -> emit(Resource.Error(""))
        }

    }
}