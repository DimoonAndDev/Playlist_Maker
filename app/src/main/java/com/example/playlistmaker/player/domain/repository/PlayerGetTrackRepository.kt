package com.example.playlistmaker.player.domain.repository

import com.example.playlistmaker.player.ui.models.PlayerTrack

interface PlayerGetTrackRepository {
    fun getPlayerTrack(trackGson:String?): PlayerTrack
}