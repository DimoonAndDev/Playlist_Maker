package com.example.playlistmaker.player.domain.repository

import android.media.MediaPlayer


interface MediaPlayerRepository  {
    fun releasePlayer()
    fun getStatus():Int
    fun playTrack()
    fun pauseTrack()
    fun preparePlayer(dataSource:String)
    fun setOnPrepareListener(listener:MediaPlayer.OnPreparedListener)

}