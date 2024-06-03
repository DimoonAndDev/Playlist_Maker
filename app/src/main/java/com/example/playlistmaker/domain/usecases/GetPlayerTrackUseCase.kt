package com.example.playlistmaker.domain.usecases
import com.example.playlistmaker.presentation.models.PlayerTrack
import com.example.playlistmaker.domain.repository.PlayerGetTrackRepository


class GetPlayerTrackUseCase (private val playerTrackRepository: PlayerGetTrackRepository) {
    fun execute(trackGson:String?): PlayerTrack {
        return playerTrackRepository.getPlayerTrack(trackGson)
    }

}