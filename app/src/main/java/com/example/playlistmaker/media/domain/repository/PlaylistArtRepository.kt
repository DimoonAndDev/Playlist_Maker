package com.example.playlistmaker.media.domain.repository


interface PlaylistArtRepository {
    fun savePLArt(uriString: String): String
    suspend fun getPLArt(playlistArtUriString: String): String
}