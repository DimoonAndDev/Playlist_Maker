package com.example.playlistmaker.media.playlist_control.domain.repository


interface PlaylistArtRepository {
    fun savePLArt(uriString: String): String
    suspend fun getPLArt(playlistArtUriString: String): String
}