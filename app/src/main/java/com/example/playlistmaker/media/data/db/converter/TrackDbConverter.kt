package com.example.playlistmaker.media.data.db.converter

import com.example.playlistmaker.media.data.db.entity.TrackEntity
import com.example.playlistmaker.search.domain.models.Track

class TrackDbConverter {
    fun map(track: TrackEntity?): Track {
        return if (track != null) Track(
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl
        )
        else return Track(trackId = -1)
    }

    fun map(track: Track): TrackEntity {
        return TrackEntity(
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            true
        )
    }
}