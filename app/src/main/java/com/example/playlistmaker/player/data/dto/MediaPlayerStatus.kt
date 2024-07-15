package com.example.playlistmaker.player.data.dto

enum class MediaPlayerStatus(val status:Int) {

    STATE_DEFAULT(0),
    STATE_PREPARED(1),
    STATE_PLAYING(2),
    STATE_PAUSED(3),

}