package com.example.playlistmaker.media.data.db.converter

import com.example.playlistmaker.media.data.db.entity.TrackInPlaylistEntity
import com.example.playlistmaker.search.domain.models.Track
import com.google.gson.Gson

class TrackInPlaylistDBConverter(private val gson: Gson) {

    fun map(track: Track, numberOfPlaylists: Int): TrackInPlaylistEntity {
        return TrackInPlaylistEntity(track.trackId, fromTrackToString(track), numberOfPlaylists)
    }

    fun map(trackInPlaylistEntity: TrackInPlaylistEntity): Track {
        return fromStringToTrack(trackInPlaylistEntity.trackJson)
    }

    private fun fromTrackToString(track: Track): String {
        return gson.toJson(track)
    }

    private fun fromStringToTrack(trackJson: String): Track {
        return gson.fromJson(trackJson, Track::class.java)
    }

}