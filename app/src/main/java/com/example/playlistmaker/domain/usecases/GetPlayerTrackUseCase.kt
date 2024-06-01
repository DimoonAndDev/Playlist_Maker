package com.example.playlistmaker.domain.usecases
import com.example.playlistmaker.domain.models.PlayerTrack
import com.example.playlistmaker.domain.repository.PlayerGetTrackRepository


class GetPlayerTrackUseCase (private val playerTrackRepository: PlayerGetTrackRepository) {
    fun execute(trackGson:String?):PlayerTrack{
        return playerTrackRepository.getPlayerTrack(trackGson)
    }

}