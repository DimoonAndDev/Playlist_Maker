package com.example.playlistmaker.domain.usecases

import com.example.playlistmaker.domain.repository.mediaplayer.MediaPlayerRepository

class MediaPlayerInteractor(
    private val mediaPlayerRepository: MediaPlayerRepository)
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