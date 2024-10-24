package com.example.playlistmaker.media.player.data.repository

import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.media.player.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.media.player.ui.mapper.TrackPlayerTrackMapper
import com.google.gson.Gson



class PlayerGetTrackRepositoryImpl(private val gson: Gson,private val trackPlayerTrackMapper: TrackPlayerTrackMapper) : PlayerGetTrackRepository {
    override fun getPlayerTrack(trackGson: String?): PlayerTrack {
        if (trackGson.isNullOrEmpty()) return PlayerTrack()
        val track = gson.fromJson(trackGson, Track::class.java)
        return trackPlayerTrackMapper.map(track)
    }
}