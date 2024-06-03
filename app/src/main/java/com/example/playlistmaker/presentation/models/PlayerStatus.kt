package com.example.playlistmaker.presentation.models

enum class PlayerStatus(val status: Int) {
    STATE_PLAYING(2),
    STATE_PAUSED(3),
    STATE_PREPARED(1),
    STATE_DEFAULT(0)
}
