package com.example.playlistmaker.domain.repository.mediaplayer

import android.media.MediaPlayer

interface PreparePlayerRepository {
    fun preparePlayer(dataSource:String):MediaPlayer
}