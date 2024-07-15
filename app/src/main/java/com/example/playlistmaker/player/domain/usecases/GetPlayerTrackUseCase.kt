package com.example.playlistmaker.player.domain.usecases
import com.example.playlistmaker.player.ui.models.PlayerTrack
import com.example.playlistmaker.player.domain.repository.PlayerGetTrackRepository


class GetPlayerTrackUseCase (private val playerTrackRepository: PlayerGetTrackRepository) {
    fun execute(trackGson:String?): PlayerTrack {
        return playerTrackRepository.getPlayerTrack(trackGson)
    }

}