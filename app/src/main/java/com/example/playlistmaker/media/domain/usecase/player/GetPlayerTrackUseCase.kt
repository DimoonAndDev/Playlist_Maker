package com.example.playlistmaker.media.domain.usecase.player

import com.example.playlistmaker.media.domain.repository.PlayerGetTrackRepository
import com.example.playlistmaker.media.ui.player.models.PlayerTrack


class GetPlayerTrackUseCase(private val playerTrackRepository: PlayerGetTrackRepository) {
    fun execute(trackGson: String?): PlayerTrack {
        return playerTrackRepository.getPlayerTrack(trackGson)
    }

}