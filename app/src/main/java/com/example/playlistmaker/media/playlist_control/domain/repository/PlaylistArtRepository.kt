package com.example.playlistmaker.media.playlist_control.domain.repository


interface PlaylistArtRepository {
    fun savePLArt(uriString: String)
    fun getPLArt(playlistArtUriString: String):String
}