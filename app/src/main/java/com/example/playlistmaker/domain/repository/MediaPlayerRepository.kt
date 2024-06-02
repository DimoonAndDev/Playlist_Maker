package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.repository.mediaplayer.GetStatusRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PauseTrackRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PlayTrackRepository
import com.example.playlistmaker.domain.repository.mediaplayer.PreparePlayerRepository
import com.example.playlistmaker.domain.repository.mediaplayer.ReleasePlayerRepository

interface MediaPlayerRepository: GetStatusRepository, PauseTrackRepository, PreparePlayerRepository,
    PlayTrackRepository, ReleasePlayerRepository {

}