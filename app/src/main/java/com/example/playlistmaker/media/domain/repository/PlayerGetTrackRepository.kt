package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.media.ui.player.models.PlayerTrack

interface PlayerGetTrackRepository {
    fun getPlayerTrack(trackGson: String?): PlayerTrack
}