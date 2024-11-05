package com.example.playlistmaker.media.data.repository.player

import com.example.playlistmaker.media.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.media.ui.player.mapper.TrackPlayerTrackMapper
import com.example.playlistmaker.media.ui.player.models.PlayerTrack
import com.example.playlistmaker.search.domain.models.Track
import com.google.gson.Gson


class PlayerGetTrackRepositoryImpl(
    private val gson: Gson,
    private val trackPlayerTrackMapper: TrackPlayerTrackMapper
) :
    PlayerGetTrackRepository {
    override fun getPlayerTrack(trackGson: String?): PlayerTrack {
        if (trackGson.isNullOrEmpty()) return PlayerTrack()
        val track = gson.fromJson(trackGson, Track::class.java)
        return trackPlayerTrackMapper.map(track)
    }
}