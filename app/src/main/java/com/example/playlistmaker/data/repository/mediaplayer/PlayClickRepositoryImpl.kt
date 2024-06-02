package com.example.playlistmaker.data.repository.mediaplayer

import android.media.MediaPlayer
import com.example.playlistmaker.data.dto.MediaPlayerInstance
import com.example.playlistmaker.data.dto.MediaPlayerStatus
import com.example.playlistmaker.domain.repository.mediaplayer.PlayClickRepository

class PlayClickRepositoryImpl(): PlayClickRepository {
    override fun clickPlayTrack(): Int {
        val mediaPlayerDTO = MediaPlayerInstance.get()
        if (mediaPlayerDTO.status == MediaPlayerStatus.STATE_PLAYING){
        mediaPlayerDTO.mediaPlayer.pause()
            mediaPlayerDTO.status = MediaPlayerStatus.STATE_PAUSED
            return mediaPlayerDTO.status.status
        }
        else{
            mediaPlayerDTO.mediaPlayer.start()
            mediaPlayerDTO.status = MediaPlayerStatus.STATE_PLAYING
            return mediaPlayerDTO.status.status
        }
    }

}