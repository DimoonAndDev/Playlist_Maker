package com.example.playlistmaker.domain.repository



interface MediaPlayerRepository  {
    fun releasePlayer()
    fun getStatus():Int
    fun playTrack()
    fun pauseTrack()
    fun preparePlayer(dataSource:String)

}