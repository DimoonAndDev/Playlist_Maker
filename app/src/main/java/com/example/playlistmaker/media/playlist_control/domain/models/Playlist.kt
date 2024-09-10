package com.example.playlistmaker.media.playlist_control.domain.models

data class Playlist(
    val name:String,
    val description:String = "",
    val artLink:String = "",
    var tracksNumber:Int = 0
)