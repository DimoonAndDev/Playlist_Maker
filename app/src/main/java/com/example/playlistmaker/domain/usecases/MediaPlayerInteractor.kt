package com.example.playlistmaker.domain.usecases

import android.media.MediaPlayer
import com.example.playlistmaker.domain.repository.mediaplayer.DeactivatePlayerRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PlayClickRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PreparePlayerRepository

class MediaPlayerInteractor(
    private val clickPlayRepository: PlayClickRepository,
    private val preparePlayerRepository: PreparePlayerRepository,
    private val deactivatePlayerRepository: DeactivatePlayerRepository
) {
    fun clickPlayTrack(){
        clickPlayRepository.clickPlayTrack()
    }
    fun preparePlayer(dataSource:String):Int{
        return preparePlayerRepository.preparePlayer(dataSource)
    }
    fun deactivatePlayer(){
        deactivatePlayerRepository.deactivatePlayer()

    }
}