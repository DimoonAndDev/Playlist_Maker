package com.example.playlistmaker.player.domain.usecases

import com.example.playlistmaker.player.domain.repository.MediaPlayerRepository

class MediaPlayerInteractor(
    private val mediaPlayerRepository: MediaPlayerRepository,
    )
 {
    fun clickPlayTrack(){
        mediaPlayerRepository.playTrack()
    }
    fun clickPauseTrack(){
        mediaPlayerRepository.pauseTrack()
    }
    fun preparePlayer(dataSource:String){
        mediaPlayerRepository.preparePlayer(dataSource)
    }
    fun releasePlayer(){
        mediaPlayerRepository.releasePlayer()

    }
     fun getStatus():Int{
         return mediaPlayerRepository.getStatus()
     }
}