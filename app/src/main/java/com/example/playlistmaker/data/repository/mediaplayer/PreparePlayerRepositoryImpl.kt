package com.example.playlistmaker.data.repository.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.content.res.AppCompatResources
import com.example.playlistmaker.R
import com.example.playlistmaker.data.dto.MediaPlayerInstance
import com.example.playlistmaker.data.dto.MediaPlayerStatus
import com.example.playlistmaker.domain.repository.mediaplayer.PreparePlayerRepository
import com.example.playlistmaker.presentation.ui.player.PlayTrackActivity
import java.security.Provider

class PreparePlayerRepositoryImpl : PreparePlayerRepository {
    private var playerState = MediaPlayerStatus.STATE_DEFAULT


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