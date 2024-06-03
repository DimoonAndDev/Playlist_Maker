package com.example.playlistmaker.presentation.mapper

import com.example.playlistmaker.presentation.models.PlayerStatus


object PlayerStatusMapper {
    fun map(status: Int): PlayerStatus {
        return when (status) {
            1 -> PlayerStatus.STATE_PREPARED
            2 -> PlayerStatus.STATE_PLAYING
            3 -> PlayerStatus.STATE_PAUSED
            else -> PlayerStatus.STATE_DEFAULT
        }
    }
}