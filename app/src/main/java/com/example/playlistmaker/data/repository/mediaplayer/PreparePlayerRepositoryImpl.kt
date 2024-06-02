package com.example.playlistmaker.data.repository.mediaplayer

import com.example.playlistmaker.data.dto.MediaPlayerInstance
import com.example.playlistmaker.data.dto.MediaPlayerStatus
import com.example.playlistmaker.domain.repository.mediaplayer.PreparePlayerRepository

class PreparePlayerRepositoryImpl : PreparePlayerRepository {



    override fun preparePlayer(dataSource: String): Int {
        val mediaPlayerDTO = MediaPlayerInstance.get()
        mediaPlayerDTO.mediaPlayer.setDataSource(dataSource)
        mediaPlayerDTO.mediaPlayer.prepareAsync()
        mediaPlayerDTO.mediaPlayer.setOnPreparedListener {
            mediaPlayerDTO.status = MediaPlayerStatus.STATE_PREPARED

        }
        mediaPlayerDTO.mediaPlayer.setOnCompletionListener {
            mediaPlayerDTO.status = MediaPlayerStatus.STATE_PREPARED
        }
        return mediaPlayerDTO.status.status
    }
}