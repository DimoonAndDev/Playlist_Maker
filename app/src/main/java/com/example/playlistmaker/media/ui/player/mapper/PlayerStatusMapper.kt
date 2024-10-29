package com.example.playlistmaker.media.ui.player.mapper

import com.example.playlistmaker.media.ui.player.models.PlayerStatus


object PlayerStatusMapper {
    fun map(status: Int?): PlayerStatus {
        return when (status) {
            1 -> PlayerStatus.STATE_PREPARED
            2 -> PlayerStatus.STATE_PLAYING
            3 -> PlayerStatus.STATE_PAUSED
            else -> PlayerStatus.STATE_DEFAULT
        }
    }
}