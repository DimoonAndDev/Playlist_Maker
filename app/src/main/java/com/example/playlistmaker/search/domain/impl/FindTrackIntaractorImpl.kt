package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.Resource
import com.example.playlistmaker.search.domain.repository.FindTrackRepository
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor
import java.util.concurrent.Executors

class FindTrackIntaractorImpl(val repository: FindTrackRepository) : FindTrackInteractor {

    private val executor = Executors.newCachedThreadPool()
    override fun findTrack(request: String, consumer: FindTrackInteractor.SearchConsumer) {
        executor.execute {

            when (val resource = repository.findTrack(request, consumer)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }

            }
        }
    }
}