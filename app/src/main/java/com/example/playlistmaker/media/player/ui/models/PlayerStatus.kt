package com.example.playlistmaker.media.player.ui.models

enum class PlayerStatus(val status: Int) {
    STATE_DEFAULT(0),
    STATE_PREPARED(1),
    STATE_PLAYING(2),
    STATE_PAUSED(3)
}
