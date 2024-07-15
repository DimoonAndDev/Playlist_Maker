package com.example.playlistmaker.search.domain.usecases


import com.example.playlistmaker.search.domain.models.Track


interface FindTrackInteractor {
    fun findTrack(request:String,consumer:SearchConsumer)
    interface SearchConsumer{
        fun consume(foundTracks:List<Track>?,errorMessage:String?)
    }
}