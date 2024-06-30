package com.example.playlistmaker.player.ui.mapper

import com.example.playlistmaker.player.ui.models.PlayerStatus


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