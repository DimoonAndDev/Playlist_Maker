package com.example.playlistmaker.data.repository.mediaplayer

import android.media.MediaPlayer
import com.example.playlistmaker.data.dto.MediaPlayerInstance
import com.example.playlistmaker.data.dto.MediaPlayerStatus
import com.example.playlistmaker.domain.repository.mediaplayer.DeactivatePlayerRepository

class DeactivatePlayerRepositoryImpl: DeactivatePlayerRepository {
    override fun deactivatePlayer() {
        val mediaPlayerDTO = MediaPlayerInstance.get()
        mediaPlayerDTO.mediaPlayer.release()
        mediaPlayerDTO.status=MediaPlayerStatus.STATE_DEFAULT
    }

}