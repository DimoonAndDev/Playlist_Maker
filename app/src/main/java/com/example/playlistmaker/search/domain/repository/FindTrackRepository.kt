package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.Resource
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.usecases.FindTrackInteractor

interface FindTrackRepository {
    fun findTrack(request:String,consumer: FindTrackInteractor.SearchConsumer):Resource<List<Track>>

}