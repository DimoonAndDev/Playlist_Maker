package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.PlayerTrack

interface PlayerGetTrackRepository {
    fun getPlayerTrack(trackGson:String?):PlayerTrack
}