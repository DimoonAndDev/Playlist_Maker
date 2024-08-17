package com.example.playlistmaker.media.player.domain.repository

import com.example.playlistmaker.media.player.ui.models.PlayerTrack

interface PlayerGetTrackRepository {
    fun getPlayerTrack(trackGson:String?): PlayerTrack
}