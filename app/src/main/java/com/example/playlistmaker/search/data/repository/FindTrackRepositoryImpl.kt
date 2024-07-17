package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.Resource
import com.example.playlistmaker.search.data.dto.SearchRequest
import com.example.playlistmaker.search.data.dto.SearchResponse
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.FindTrackRepository
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import org.koin.java.KoinJavaComponent.getKoin

class FindTrackRepositoryImpl : FindTrackRepository {
    private val retrofitNetworkClient = getKoin().get<RetrofitNetworkClient>()

    override fun findTrack(
        request: String,
        consumer: FindTrackInteractor.SearchConsumer
    ): Resource<List<Track>> {

        val response = retrofitNetworkClient.doRequest(SearchRequest(request))
        return when (response.resultCode) {
            -1 -> Resource.Error(request)
            200 -> Resource.Success((response as SearchResponse).results.map {
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
            })

            else -> Resource.Error("")
        }

    }
}