package com.example.playlistmaker.media.data.db.converter

import com.example.playlistmaker.media.data.db.entity.TrackEntity
import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import com.example.playlistmaker.search.domain.models.Track

class TrackDbConverter {
    fun map(track: TrackEntity?): PlayerTrack? {
        return if (track != null) PlayerTrack(
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl512,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl?:"",
            track.trackId,
        )
        else return null
    }
    fun map(track: TrackEntity?,mapperFlag:Boolean): Track? {
        return if (track != null) Track(
            track.trackName,
            track.artistName,
            track.trackTimeMillisInt,
            track.artworkUrl512?.replaceAfterLast("/", "100x100bb.jpg"),
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl?:"",

        )
        else return null
    }

    fun map(track: PlayerTrack): TrackEntity {
        return TrackEntity(
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl512,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            true,
            track.trackTimeMillisInt
        )
    }
}