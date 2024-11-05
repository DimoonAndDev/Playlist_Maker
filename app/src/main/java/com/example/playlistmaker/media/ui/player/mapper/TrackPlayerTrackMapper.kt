package com.example.playlistmaker.media.ui.player.mapper

import com.example.playlistmaker.R
import com.example.playlistmaker.media.ui.player.models.PlayerTrack
import com.example.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackPlayerTrackMapper {
    fun map(track: Track): PlayerTrack {
        val artworkUrl512 = track.artworkUrl100?.replaceAfterLast("/", "512x512bb.jpg")
        val trackTimeMillis =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
                .ifEmpty { R.string.track_time_placeholder.toString() }
        return PlayerTrack(
            track.trackName.ifEmpty { R.string.track_name_placeholder.toString() },
            track.artistName.ifEmpty { R.string.track_artist_placeholder.toString() },
            trackTimeMillis,
            artworkUrl512,
            track.collectionName,
            track.releaseDate ?: R.string.trackplay_title_year.toString(),
            track.primaryGenreName ?: R.string.trackplay_title_genre.toString(),
            track.country ?: R.string.trackplay_title_country.toString(),
            track.previewUrl
                ?: "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview126/v4/48/8f/a4/488fa4b5-b606-71ee-572e-691f840503c8/mzaf_15586272016916254191.plus.aac.p.m4a",
            track.trackId,
            track.trackTimeMillis
        )
    }

    fun map(playerTrack: PlayerTrack): Track {
        val artworkUrl100 = playerTrack.artworkUrl512?.replaceAfterLast("/", "512x512bb.jpg")
        return Track(
            playerTrack.trackName,
            playerTrack.artistName,
            playerTrack.trackTimeMillisInt,
            artworkUrl100,
            playerTrack.trackId,
            playerTrack.collectionName,
            playerTrack.releaseDate,
            playerTrack.primaryGenreName,
            playerTrack.country,
            playerTrack.previewUrl
        )
    }
}