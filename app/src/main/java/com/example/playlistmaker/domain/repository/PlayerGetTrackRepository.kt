package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.presentation.models.PlayerTrack

interface PlayerGetTrackRepository {
    fun getPlayerTrack(trackGson:String?): PlayerTrack
}