package com.example.playlistmaker.media.player.data.repository

import com.example.playlistmaker.R
import com.example.playlistmaker.media.player.ui.models.PlayerTrack
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.media.player.domain.repository.PlayerGetTrackRepository
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerGetTrackRepositoryImpl : PlayerGetTrackRepository {
    override fun getPlayerTrack(trackGson: String?): PlayerTrack {
        if (trackGson.isNullOrEmpty()) return PlayerTrack()
        val track = Gson().fromJson(trackGson, Track::class.java)
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
}