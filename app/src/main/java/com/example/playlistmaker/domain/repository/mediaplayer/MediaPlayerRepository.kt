package com.example.playlistmaker.domain.repository.mediaplayer

interface MediaPlayerRepository {
    fun preparePlayer(trackURL : String)
    fun playTrack()
    fun pauseTrack()
    fun releasePlayer()
    fun getStatus():Int
}